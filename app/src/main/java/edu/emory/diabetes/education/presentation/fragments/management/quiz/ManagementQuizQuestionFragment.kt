package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementQuizQuestionBinding
import edu.emory.diabetes.education.presentation.AnswerAdapter
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapterEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ManagementQuizQuestionFragment : BaseFragment(R.layout.fragment_management_quiz_question) {
    private val viewModel: ManagementQuizQuestionViewModel by viewModels()
    private val args: ManagementQuizQuestionFragmentArgs by navArgs()

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
            viewModel.selectQuestions(args.quizId).onEach { questionEntity ->
                adapter = ManagementQuizQuestionAdapter {
                    when (it) {
                        QuizAdapterEvent.MaximumLimit ->
                            Toast.makeText(
                                requireContext(),
                                "Maximum number of entries reached",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }.also { adapter ->
                    with(questionEntity.first()) {
                        question.text = title
                        if (description.isEmpty()) subtitle.visibility = View.GONE
                        subtitle.text = description
                        adapter.maxAnswerSize = maxAnswerSize
                        adapter.asyncListDiffer.submitList(choices)
                    }
                }
                next.setOnClickListener {
                    val answers = ManagementQuizUtils.answer
                    answers.isNotEmpty().also {
                        if (it) {
                            if (questionEntity.first().answers.all { answers.contains(it) }) {
                                iconAnswer.apply {
                                    visibility = View.VISIBLE
                                    setImageDrawable(
                                        ContextCompat.getDrawable(
                                            requireContext(),
                                            R.drawable.ic_correct_answer
                                        )
                                    )
                                }
                                answerRecyclerView.apply {
                                    visibility = View.VISIBLE
                                    answerAdapter = AnswerAdapter().also {
                                        it.submitList(answers)
                                    }
                                }
                                next.text = "Next"
                                if (selectedChoices.visibility==View.VISIBLE){
                                    selectedChoices.visibility = View.GONE
                                }
                                next.setOnClickListener {
                                    ManagementQuizQuestionFragmentDirections
                                        .actionManagementQuizQuestionFragmentToQuizManagementFinishFragment()
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                }

                            } else {
                                iconAnswer.apply {
                                    visibility = View.VISIBLE
                                    setImageDrawable(
                                        ContextCompat.getDrawable(
                                            requireContext(),
                                            R.drawable.ic_wrong_answer
                                        )
                                    )
                                }
                                answerRecyclerView.apply {
                                    visibility = View.VISIBLE
                                    answerAdapter = AnswerAdapter().also {
                                        it.submitList(answers)
                                    }
                                }
                                next.text = "Submit"
                                selectedChoices.visibility = View.VISIBLE

                            }
                        }
                    }

                }

            }.launchIn(lifecycleScope)


        }
    }

}