package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentAboutUsHomeBinding
import edu.emory.diabetes.education.presentation.AboutUsAdapter
import edu.emory.diabetes.education.presentation.BaseFragment


class AboutUsHomeFragment : BaseFragment(R.layout.fragment_about_us__home) {

    private lateinit var binding: FragmentAboutUsHomeBinding
    private lateinit var adapter: AboutUsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAboutUsHomeBinding.bind(view)

        binding.aboutUsTabLayout.addTab(binding.aboutUsTabLayout.newTab().setText("Content"))
        binding.aboutUsTabLayout.addTab(binding.aboutUsTabLayout.newTab().setText("Team"))

        val fragmentManager = childFragmentManager
        adapter = AboutUsAdapter(fragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter

        binding.aboutUsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.aboutUsTabLayout.selectTab(binding.aboutUsTabLayout.getTabAt(position))

                val selectedTabIndex = binding.aboutUsTabLayout.selectedTabPosition
                if (selectedTabIndex == 0) {
                    binding.aboutUsTabLayout.setSelectedTabIndicator(R.drawable.about_us_tab_indicator_left)
                } else if (selectedTabIndex == 1) {
                    binding.aboutUsTabLayout.setSelectedTabIndicator(R.drawable.about_us_tab_indicator_right)
                }
            }
        })

    }




}