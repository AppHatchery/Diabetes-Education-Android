package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionBinding
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz
import edu.emory.diabetes.education.presentation.BaseFragment

class NutritionFragment : BaseFragment(R.layout.fragment_diabetes_nutrition), NutritionNavigator {
    private lateinit var basicViewPagerAdapter: NutritionViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentDiabetesNutritionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDiabetesNutritionBinding.bind(view)
        val tabs = listOf("Lesson", "Quiz")
        basicViewPagerAdapter = NutritionViewPagerAdapter(this, tabs.size)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = basicViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun invoke(lesson: Lesson?, quiz: Quiz?, event: Event) {
        when (event) {
            Event.Quiz -> {
                quiz?.let {
                    NutritionFragmentDirections
                        .actionNutritionFragmentToQuizNutritionFragment(quiz).apply {
                            findNavController().navigate(this)
                        }
                }
            }
            Event.Lesson ->
                lesson?.let {
                    NutritionFragmentDirections
                        .actionNutritionFragmentToWhatIsDiabetes(lesson).apply {
                            findNavController().navigate(this)
                        }
                }
        }

    }
}