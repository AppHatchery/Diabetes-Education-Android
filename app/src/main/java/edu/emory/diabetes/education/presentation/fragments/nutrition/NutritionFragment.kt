package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsBinding
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionBinding
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.BasicFragmentDirections
import edu.emory.diabetes.education.presentation.fragments.basic.BasicNavigator
import edu.emory.diabetes.education.presentation.fragments.basic.BasicViewPagerAdapter
import edu.emory.diabetes.education.presentation.fragments.basic.Event

class NutritionFragment : BaseFragment(R.layout.fragment_diabetes_nutrition), BasicNavigator {
    private lateinit var basicViewPagerAdapter: BasicViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentDiabetesNutritionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDiabetesNutritionBinding.bind(view)
        val tabs = listOf("Lesson", "Quiz")
        basicViewPagerAdapter = BasicViewPagerAdapter(this, tabs.size)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = basicViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun invoke(lesson: Lesson?, event: Event) {
        when (event) {
            Event.Quiz ->{}

            Event.Lesson ->

                lesson?.let {
                    NutritionFragmentDirections
                        .actionNutritionFragmentToNutritionWebViewFragment(lesson).apply {
                        findNavController().navigate(this)
                    }

                }
        }

    }
}