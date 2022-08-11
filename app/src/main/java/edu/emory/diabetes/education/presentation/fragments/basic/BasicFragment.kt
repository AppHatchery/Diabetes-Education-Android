package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class BasicFragment : BaseFragment(R.layout.fragment_diabetes_basics), BasicNavigator {
    private lateinit var basicViewPagerAdapter: BasicViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentDiabetesBasicsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDiabetesBasicsBinding.bind(view)
        val tabs = listOf("Lesson", "Quiz")
        basicViewPagerAdapter = BasicViewPagerAdapter(this, tabs.size)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = basicViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun invoke(path: String?, event: Event) {

        when (event) {
            Event.Quiz ->
                BasicFragmentDirections
                    .actionDiabetesBasicsFragmentToQuizFragment().apply {
                        findNavController().navigate(this)
                    }

            Event.Lesson ->
                BasicFragmentDirections
                    .actionDiabetesBasicsFragmentToWhatIsDiabetes("index").apply {
                        findNavController().navigate(this)
                    }
        }

    }


}


