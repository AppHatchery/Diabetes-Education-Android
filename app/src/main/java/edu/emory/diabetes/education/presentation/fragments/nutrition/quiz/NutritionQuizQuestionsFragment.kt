package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

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
import edu.emory.diabetes.education.databinding.FragmentNutritionQuizQuestionsBinding
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.AnswerAdapter
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NutritionQuizQuestionsFragment : BaseFragment(R.layout.fragment_nutrition_quiz_questions),
    AnswerProcessorUtil.OnSubmitResultStateListener {
    private val viewModel: NutritionQuizQuestionViewModel by viewModels()
    private val args: NutritionQuizQuestionsFragmentArgs by navArgs()
    lateinit var choices: List<Choice>
    private lateinit var questionItem: Question
    private var quizFinished: Boolean = false
    private lateinit var selectedChoices: AppCompatTextView
    private lateinit var root: FragmentNutritionQuizQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuizNutritionUtil.answer.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedChoices = view.findViewById(R.id.selectedChoices)
        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title} : Questions"
        }.launchIn(lifecycleScope)

        with(FragmentNutritionQuizQuestionsBinding.bind(view)) {
            this@NutritionQuizQuestionsFragment.root = this
            viewModel.selectQuestions(args.quizId).onEach { questionEntity ->
                adapter = QuizNutritionAdapter(viewModel) {
                    when (it) {
                        QuizAdapterEvent.MaximumLimit -> {
                            /*Toast.makeText(
                                requireContext(),
                                "Maximum number of entries reached",
                                Toast.LENGTH_SHORT
                            ).show()*/
                        }
                        QuizAdapterEvent.ItemClicked -> {
                            if (viewModel.quizFinished.value) {
                                val listener = getListener(this, questionEntity)
                                next.apply {
                                    text = "Submit"
                                    setOnClickListener(listener)
                                }
                                viewModel.setQuizFinished(false)
                            }
                            hideView(iconAnswer)
                            resultInfoTextView.apply {
                                text = ""
                                hideView(this)
                            }
                            hideView(selectedChoices)
                        }
                    }
                }.also { adapter ->
                    with(questionEntity.first()) {
                        question.text = title
                        if (description.isEmpty()) subtitle.visibility = View.GONE
                        subtitle.text = description
                        adapter.maxAnswerSize = maxAnswerSize
                        adapter.asyncListDiffer.submitList(choices)
                        adapter.listener = this@NutritionQuizQuestionsFragment
                        questionItem = this
                    }
                }
                val listener = getListener(this, questionEntity)
                next.setOnClickListener(listener)
            }.launchIn(lifecycleScope)

        }
    }

    override fun onSubmitResultState(
        resultInfo: AnswerProcessorUtil.RESULTS_ON_SUBMIT,
        answerChoices: String, hasSomeAllCorrect: Boolean
    ) {
        when (resultInfo) {
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_ALL_CORRECT -> {
                this@NutritionQuizQuestionsFragment.root.apply {
                    quizFinished = true
                    hideView(answerRecyclerView)
                }
            }
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT -> {
                this@NutritionQuizQuestionsFragment.root.apply {
                    if (hasSomeAllCorrect) {
                        answerRecyclerView.apply {
                            visibility = View.GONE
                            iconAnswer.visibility = View.GONE
                            resultInfoTextView.visibility = View.GONE
                            selectedChoices.apply {
                                setTextColor(ContextCompat.getColor(context, R.color.red_900))
                                visibility = View.VISIBLE
                                text = answerChoices
                            }
                        }
                    } else {
                        hideView(answerRecyclerView)
                        hideView(selectedChoices)
                        showView(iconAnswer)
                        showView(resultInfoTextView)
                        resultInfoTextView.text = answerChoices
                    }
                }
            }
            AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_NONE_CORRECT -> {
                this@NutritionQuizQuestionsFragment.root.apply {
                    hideView(answerRecyclerView)
                    showView(resultInfoTextView)
                    showView(iconAnswer)
                    resultInfoTextView.text = answerChoices
                }
            }

        }
    }
    private fun clearPreviousState(resultView: View, answerView: View, selectedView: View) {
        resultView.visibility = View.GONE
        answerView.visibility = View.GONE
        selectedView.visibility = View.GONE
    }
    private fun hideView(view: View) {
        if (view.visibility == View.VISIBLE) view.visibility = View.GONE
    }
    private fun showView(view: View) {
        if (view.visibility == View.GONE) view.visibility = View.VISIBLE
    }
    private fun getListener(
        binding: FragmentNutritionQuizQuestionsBinding,
        questionEntity: List<Question>
    ): View.OnClickListener {
        val listener = View.OnClickListener {
            binding.apply {
                val answers = QuizNutritionUtil.answer
                clearPreviousState(resultInfoTextView, answerRecyclerView, selectedChoices)
                answers.isNotEmpty().also {
                    if (it) {
                        if (questionEntity.first().answers.all { answers.contains(it) }) {
                            val answerList = answers as List<String>
                            if (adapter != null) {
                                (adapter as? QuizNutritionAdapter)?.apply {
                                    setAnswers(questionItem, args.quizId, answerList)
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
                                visibility = View.VISIBLE
                                text = answers.joinToString(separator = ", ")
                            }
                            answerRecyclerView.apply {
                                //visibility = View.VISIBLE
                                answerAdapter = AnswerAdapter().also {
                                    it.submitList(answers)
                                }
                            }
                            viewModel.setQuizFinished(true)
                            if (viewModel.quizFinished.value) {
                                next.text = "Next"
                                next.setOnClickListener {
                                    QuizQuestionFragmentDirections
                                        .actionQuizQuestionFragmentToQuizFinishFragment()
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                }
                            }
                        } else {
                            val answerList = answers as List<String>
                            if (adapter != null) {
                                (adapter as? QuizNutritionAdapter)?.apply {
                                    setAnswers(questionItem, args.quizId, answerList)
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
        }
        return listener
    }
}