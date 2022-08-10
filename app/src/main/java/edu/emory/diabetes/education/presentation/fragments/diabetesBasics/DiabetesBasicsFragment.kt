package edu.emory.diabetes.education.presentation.fragments.diabetesBasics

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class DiabetesBasicsFragment : BaseFragment(R.layout.fragment_diabetes_basics) {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentDiabetesBasicsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDiabetesBasicsBinding.bind(view)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter


        val tabs = listOf("Lesson", "Quiz")

        viewPager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

    }


}


