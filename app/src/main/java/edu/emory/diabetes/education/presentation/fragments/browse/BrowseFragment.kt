package edu.emory.diabetes.education.presentation.fragments.browse

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentBrowseBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class BrowseFragment : BaseFragment(R.layout.fragment_browse) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentBrowseBinding.bind(view)) {
            val tabs = listOf("Content", "Bookmarks", "Notes")
            viewPager.adapter = BrowseViewPagerAdapter(this@BrowseFragment, tabs.size)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

}