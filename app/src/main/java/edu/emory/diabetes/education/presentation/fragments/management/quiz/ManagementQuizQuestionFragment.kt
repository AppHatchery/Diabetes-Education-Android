package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementQuizQuestionBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ManagementQuizQuestionFragment: BaseFragment(R.layout.fragment_management_quiz_question) {
    private val viewModel: ManagementQuizQuestionViewModel by viewModels()
    private val args: ManagementQuizQuestionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ManagementQuizUtils.answer.clear()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val quiz = ManagementQuizUtils.questions[0]

        viewModel.getQuizCode(args.quizId).onEach {
            (requireActivity() as AppCompatActivity)
                .supportActionBar?.title = "${it.title}: Questions"
        }

        with(FragmentManagementQuizQuestionBinding.bind(view)){
            adapter = ManagementQuizAdapter {
            }.also { adapter ->
                viewModel.selectQuestions(args.quizId).onEach {
                    question.text = it[0].title
                    adapter.submitList(it[0].choices)
                }.launchIn(lifecycleScope)
            }
            next.setOnClickListener {
                val ans = ManagementQuizUtils.answer
                ans.isNotEmpty().also {
                    if(it){
                        if(quiz.answers.contains(ans.get(0))){
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
                        }else{
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