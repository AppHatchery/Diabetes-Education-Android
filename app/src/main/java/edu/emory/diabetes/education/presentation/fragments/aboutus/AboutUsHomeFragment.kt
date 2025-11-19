package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentAboutUsHomeBinding
import edu.emory.diabetes.education.presentation.AboutUsAdapter
import edu.emory.diabetes.education.presentation.BaseFragment


class AboutUsHomeFragment : BaseFragment(R.layout.fragment_about_us__home) {

    private lateinit var adapter: AboutUsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // binding = FragmentAboutUsHomeBinding.bind(view)



        with(FragmentAboutUsHomeBinding.bind(view)) {
//        aboutUsTabLayout.addTab(aboutUsTabLayout.newTab().setText("Content"))
//        aboutUsTabLayout.addTab(aboutUsTabLayout.newTab().setText("Team"))
            val tabContent = LayoutInflater.from(requireContext()).inflate(R.layout.about_us_custom_tab_text, null)
            val tabContentTextView_content= tabContent.findViewById<TextView>(R.id.tabTextView)
            tabContentTextView_content.text = "Content"
            aboutUsTabLayout.addTab(aboutUsTabLayout.newTab().setCustomView(tabContent))

            val tabTeam = LayoutInflater.from(requireContext()).inflate(R.layout.about_us_custom_tab_text, null)
            val tabTeamTextView_team = tabTeam.findViewById<TextView>(R.id.tabTextView)
            tabTeamTextView_team.text = "Team"
            aboutUsTabLayout.addTab(aboutUsTabLayout.newTab().setCustomView(tabTeam))

        val fragmentManager = childFragmentManager
        adapter = AboutUsAdapter(fragmentManager, lifecycle)
        viewPager2.adapter = adapter

        aboutUsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                aboutUsTabLayout.selectTab(aboutUsTabLayout.getTabAt(position))

                when (aboutUsTabLayout.selectedTabPosition) {
                    0 -> {
                        aboutUsTabLayout.setSelectedTabIndicator(R.drawable.about_us_tab_indicator_left)
                        tabContentTextView_content.setTextColor(Color.WHITE)
                        tabTeamTextView_team.setTextColor(Color.GRAY)
                    }
                    1 -> {
                        aboutUsTabLayout.setSelectedTabIndicator(R.drawable.about_us_tab_indicator_right)
                        tabContentTextView_content.setTextColor(Color.GRAY)
                        tabTeamTextView_team.setTextColor(Color.WHITE)
                    }
                    else -> {

                    }
                }
            }
        })

    }


    }
}