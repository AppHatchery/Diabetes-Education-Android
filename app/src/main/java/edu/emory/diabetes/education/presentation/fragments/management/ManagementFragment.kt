package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementBinding
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.presentation.BaseFragment

class ManagementFragment : BaseFragment(R.layout.fragment_management), ManagementNavigator {
    private lateinit var managementViewPagerAdapter: ManagementViewPagerAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmentManagementBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentManagementBinding.bind(view)
        val tabs = listOf("Lesson", "Quiz")
        managementViewPagerAdapter = ManagementViewPagerAdapter(this, tabs.size)
        viewPager2 = view.findViewById(R.id.managementViewPager)
        viewPager2.adapter = managementViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager2) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun invoke(lesson: Lesson?, event: ManagementEvent) {
        when (event) {
            ManagementEvent.Quiz -> {}

            ManagementEvent.Lesson ->
                lesson?.let {
                    ManagementFragmentDirections
                        .actionManagementFragmentToBloodSugarMonitoringFragment3(it).apply {
                            findNavController().navigate(this)
                        }
                }

        }
    }

}