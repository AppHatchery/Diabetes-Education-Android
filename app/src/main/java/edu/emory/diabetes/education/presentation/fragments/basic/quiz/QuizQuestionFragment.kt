package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.AnswerAdapter
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QuizQuestionFragment : BaseFragment(R.layout.fragment_quiz_question) ,AnswerProcessorUtil.OnSubmitResultStateListener{
    val viewModel: QuizQuestionViewModel by viewModels()
    private val args: QuizQuestionFragmentArgs by navArgs()
    lateinit var choices:List<Choice>
    private lateinit var questionItem:Question
    private var quizFinished: Boolean =false
    private lateinit var selectedChoices: AppCompatTextView
    private lateinit var root:FragmentQuizQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuizUtils.answer.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       selectedChoices = view.findViewById(R.id.selectedChoices)
        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title} : Questions"
        }.launchIn(lifecycleScope)

        with(FragmentQuizQuestionBinding.bind(view)) {
            this@QuizQuestionFragment.root=this
            viewModel.selectQuestions(args.quizId).onEach { questionEntity ->
                adapter = QuizAdapter {
                    when (it) {
                        QuizAdapterEvent.MaximumLimit ->{
                            Toast.makeText(
                                requireContext(),
                                "Maximum number of entries reached",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        QuizAdapterEvent.ItemClicked -> {
                            if (quizFinished){
                                Toast.makeText(requireContext(),"Item Clicked $quizFinished",Toast.LENGTH_SHORT).show()

                                this.resultInfoTextView.apply {
                                    //showView(this)
                                }
                            }
                            //quizFinished=false



                        }
                    }
                }.also { adapter ->
                    with(questionEntity.first()) {
                        question.text = title
                        if (description.isEmpty()) subtitle.visibility = View.GONE
                        subtitle.text = description
                        adapter.maxAnswerSize = maxAnswerSize
                        adapter.asyncListDiffer.submitList(choices)
                        adapter.listener =this@QuizQuestionFragment
                        questionItem=this
                    }
                }
                next.setOnClickListener {
                    val answers = QuizUtils.answer
                    clearPreviousState(resultInfoTextView, answerRecyclerView, selectedChoices)
                    answers.isNotEmpty().also {
                        if (it) {
                            if (questionEntity.first().answers.all { answers.contains(it) }) {
                                val answerList = answers as List<String>
                                if (adapter != null) {
                                    (adapter as? QuizAdapter)?.apply {
                                        setAnswers(questionItem,args.quizId,answerList)
                                    }
                                }
                                iconAnswer.apply {
                                    visibility = View.VISIBLE
                                    setImageDrawable(
                                        ContextCompat.getDrawable(
                                            requireContext(),
                                            R.drawable.ic_correct_answer
                                        )
                                    )
                                }
                                resultInfoTextView.apply {
                                    visibility =View.VISIBLE
                                    text = answers.joinToString(separator = ", ")
                                }

                                answerRecyclerView.apply {
                                    //visibility = View.VISIBLE
                                    answerAdapter = AnswerAdapter().also {
                                        
                                        it.submitList(answers)
                                    }
                                }
                                next.text = "Next"
                                next.setOnClickListener {
                                    QuizQuestionFragmentDirections
                                        .actionQuizQuestionFragmentToQuizFinishFragment()
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                }
                            } else {
                                val answerList = answers as List<String>
                                if (adapter != null) {
                                    (adapter as? QuizAdapter)?.apply {
                                        setAnswers(questionItem,args.quizId,answerList)
                                    }
                                }
                                iconAnswer.apply {
                                    //visibility = View.VISIBLE
                                    setImageDrawable(
                                        ContextCompat.getDrawable(
                                            requireContext(),
                                            R.drawable.ic_wrong_answer
                                        )
                                    )
                                }
                                answerRecyclerView.apply {
                                    //visibility = View.VISIBLE
                                    answerAdapter = AnswerAdapter().also {
                                        it.submitList(answers)
                                    }
                                }
                                next.text = "Submit"
                            }
                        }
                    }

                }

            }.launchIn(lifecycleScope)

        }
    }

    override fun onSubmitResultState(
        resultInfo: AnswerProcessorUtil.RESULTS_ON_SUBMIT,
        answerChoices:String,hasSomeAllCorrect:Boolean
    ) {
        when(resultInfo){
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_ALL_CORRECT-> {
                this@QuizQuestionFragment.root.apply {
                    quizFinished =true
                    hideView(answerRecyclerView)

                }
            }
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT->{
                this@QuizQuestionFragment.root.apply {
                    if (hasSomeAllCorrect){
                        answerRecyclerView.apply {
                            visibility= View.GONE
                            iconAnswer.visibility = View.GONE
                            resultInfoTextView.visibility = View.GONE
                            selectedChoices.apply {
                                setTextColor(ContextCompat.getColor(context, R.color.red_900))
                                visibility =View.VISIBLE
                                text= answerChoices
                            }
                        }
                    }else{
                        hideView(answerRecyclerView)
                        hideView(selectedChoices)
                        showView(iconAnswer)
                        showView(resultInfoTextView)
                        resultInfoTextView.text = answerChoices
                        selectedChoices.apply {
                            //if (this.visibility == View.GONE) this.visibility = View.VISIBLE
                            //this.text = answerChoices
                        }
                    }
                }
            }
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_NONE_CORRECT->{
                this@QuizQuestionFragment.root.apply {
                    hideView(answerRecyclerView)
                    showView(resultInfoTextView)
                    showView(iconAnswer)
                    resultInfoTextView.text = answerChoices
                }
                //Log.e("ON SUBMIT RESUTLS",AnswerProcessorUtil.SubmissionResultStates.IS_NOT_RIGHT_CHOOSE_AGAIN)
            }

        }
    }
    private fun clearPreviousState(resultView: View, answerView: View, selectedView: View){
        resultView.visibility = View.GONE
        answerView.visibility = View.GONE
        selectedView.visibility = View.GONE
    }
    private fun hideView(view:View){
        if (view.visibility == View.VISIBLE) view.visibility= View.GONE
    }
    private fun showView(view:View){
        if (view.visibility == View.GONE) view.visibility= View.VISIBLE
    }
    private fun resetText(view:AppCompatTextView){
        view.text =""
    }


    private fun refreshQuiz(finished:Boolean){
        if (finished) this.quizFinished =false
    }
}