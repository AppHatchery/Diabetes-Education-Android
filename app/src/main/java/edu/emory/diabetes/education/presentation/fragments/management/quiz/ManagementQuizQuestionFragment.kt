package edu.emory.diabetes.education.presentation.fragments.management.quiz

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
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.databinding.FragmentManagementQuizQuestionBinding
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.AnswerAdapter
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ManagementQuizQuestionFragment : BaseFragment(R.layout.fragment_management_quiz_question),Utils.OnSubmitResultStateListener {
    private val viewModel: ManagementQuizQuestionViewModel by viewModels()
    private val args: ManagementQuizQuestionFragmentArgs by navArgs()
    private lateinit var root: FragmentManagementQuizQuestionBinding
    lateinit var choices: List<Choice>
    private lateinit var questionItem: Question
    private var quizFinished: Boolean = false
    private lateinit var selectedChoices: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ManagementQuizUtils.answer.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title} : Questions"
        }.launchIn(lifecycleScope)
        with(FragmentManagementQuizQuestionBinding.bind(view)) {

            this@ManagementQuizQuestionFragment.root = this
            viewModel.selectQuestions(args.quizId).onEach { questionEntity ->
                adapter = ManagementQuizQuestionAdapter(viewModel) {
                    when (it) {
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
                        else -> {}
                    }
                }.also { adapter ->
                    with(questionEntity.first()) {
                        question.text = title
                        if (description.isEmpty()) subtitle.visibility = View.GONE
                        subtitle.text = description
                        adapter.maxAnswerSize = maxAnswerSize
                        adapter.maxChoicesSize = choices.size
                        adapter.asyncListDiffer.submitList(choices)
                        adapter.listener = this@ManagementQuizQuestionFragment
                        questionItem = this
                    }
                }
                val listener = getListener(this, questionEntity)
                next.setOnClickListener(listener)

            }.launchIn(lifecycleScope)
        }
    }

    override fun onSubmitResultState(
        resultInfo: Utils.RESULTS_ON_SUBMIT,
        answerChoices: String, hasSomeAllCorrect: Boolean
    ) {
        when (resultInfo) {
            Utils.RESULTS_ON_SUBMIT.HAS_ALL_CORRECT -> {
                this@ManagementQuizQuestionFragment.root.apply {
                    quizFinished = true
                    iconAnswer.apply {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_correct_answer
                            )
                        )
                        showView(this)
                    }
                    resultInfoTextView.apply {
                        showView(this)
                        text =answerChoices
                    }
                    showView(resultInfoTextView)
                }
            }
            Utils.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT -> {
                this@ManagementQuizQuestionFragment.root.apply {
                    if (hasSomeAllCorrect) {
                        selectedChoices.apply {
                            setTextColor(ContextCompat.getColor(context, R.color.red_900))
                            visibility = View.VISIBLE
                            text = answerChoices
                        }
                    } else {
                        iconAnswer.apply {
                            setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_wrong_answer
                                )
                            )
                            showView(this)
                        }
                        hideView(selectedChoices)
                        showView(resultInfoTextView)
                        resultInfoTextView.text = answerChoices
                    }
                }
            }
            Utils.RESULTS_ON_SUBMIT.HAS_NONE_CORRECT -> {
                this@ManagementQuizQuestionFragment.root.apply {
                    iconAnswer.apply {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_wrong_answer
                            )
                        )
                    }
                    showView(resultInfoTextView)
                    showView(iconAnswer)
                    resultInfoTextView.text = answerChoices
                }
            }

        }
    }
    private fun clearPreviousState(resultView: View, selectedView: View) {
        resultView.visibility = View.GONE
        selectedView.visibility = View.GONE
    }
    private fun hideView(view: View) {
        if (view.visibility == View.VISIBLE) view.visibility = View.GONE
    }
    private fun showView(view: View) {
        if (view.visibility == View.GONE) view.visibility = View.VISIBLE
    }
    private fun getListener(
        binding: FragmentManagementQuizQuestionBinding,
        questionEntity: List<Question>
    ): View.OnClickListener {
        val listener = View.OnClickListener {
            binding.apply {
                val answers = ManagementQuizUtils.answer
                clearPreviousState(resultInfoTextView, selectedChoices)
                answers.isNotEmpty().also {
                    if (it) {
                        if (Utils.hasAllAnswers(
                                answers,
                                questionEntity.first().answers
                            )
                        ) {
                            val answerList = answers as List<String>
                            if (adapter != null) {
                                (adapter as? ManagementQuizQuestionAdapter)?.apply {
                                    setAnswers(questionItem, args.quizId, answerList)
                                }
                            }
                            viewModel.setQuizFinished(true).also {
                                next.text = "Next"
                                next.setOnClickListener {
                                    ManagementQuizQuestionFragmentDirections
                                        .actionManagementQuizQuestionFragmentToQuizManagementFinishFragment()
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                }
                            }

                        } else {
                            val answerList = answers as List<String>
                            if (adapter != null) {
                                (adapter as? ManagementQuizQuestionAdapter)?.apply {
                                    setAnswers(questionItem, args.quizId, answerList)
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