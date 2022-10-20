package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentNutritionQuizQuestionsBinding
import edu.emory.diabetes.education.presentation.AnswerAdapter
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapterEvent
import edu.emory.diabetes.education.presentation.fragments.management.quiz.ManagementQuizQuestionAdapter
import edu.emory.diabetes.education.presentation.fragments.management.quiz.ManagementQuizUtils
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NutritionQuizQuestionsFragment : BaseFragment(R.layout.fragment_nutrition_quiz_questions) {
    private val viewModel: NutritionQuizQuestionViewModel by viewModels()
    private val args: NutritionQuizQuestionsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuizNutritionUtil.answer.clear()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val quiz = QuizNutritionUtil.questions[0]

        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title} : Questions"
        }.launchIn(lifecycleScope)

        with(FragmentNutritionQuizQuestionsBinding.bind(view)){
            viewModel.selectQuestions(args.quizId).onEach { questionEntity->
                adapter = QuizNutritionAdapter {
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
                        adapter.maxAnswerSize = maxAnswerSize
                        adapter.asyncListDiffer.submitList(choices)
                    }
                }
                next.setOnClickListener {
                    val answers = QuizNutritionUtil.answer
                    answers.isNotEmpty().also {
                        if (it) {
                            if (questionEntity.first().answers.all{ answers.contains(it) }) {
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
                                selectedChoices.visibility = View.VISIBLE
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