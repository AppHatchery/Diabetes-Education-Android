package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentNutritionQuizQuestionsBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.management.quiz.ManagementQuizUtils
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
        val quiz = ManagementQuizUtils.questions[0]

        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title} : Questions"
        }.launchIn(lifecycleScope)

        with(FragmentNutritionQuizQuestionsBinding.bind(view)){
            adapter = QuizNutritionAdapter {
            }.also { adapter->
                viewModel.selectQuestions(args.quizId).onEach {
                    question.text = it[0].title
                    adapter.submitList(it[0].choices)
                }.launchIn(lifecycleScope)
            }
            next.setOnClickListener {
                val ans = QuizNutritionUtil.answer
                ans.isNotEmpty().also {
                    if (it){
                        if (quiz.answers.contains(ans.get(0))){
                            iconAnswer.apply {
                                visibility = View.VISIBLE
                                setImageDrawable(requireContext().getDrawable(R.drawable.ic_correct_answer))
                            }
                            answer.apply {
                                visibility = View.VISIBLE
                                text = ans.get(ans.size.minus(1))
                            }
                            next.text = "Next"
                            selectedChoices.visibility = View.VISIBLE
                        } else{
                            iconAnswer.apply {
                                visibility = View.VISIBLE
                                setImageDrawable(requireContext().getDrawable(R.drawable.ic_wrong_answer))
                            }
                            answer.apply {
                                visibility = View.VISIBLE
                                text = ans.get(ans.size.minus(1))
                            }
                            next.text = "Submit"
                            selectedChoices.visibility = View.VISIBLE

                        }
                    }
                }

            }
        }
    }
}