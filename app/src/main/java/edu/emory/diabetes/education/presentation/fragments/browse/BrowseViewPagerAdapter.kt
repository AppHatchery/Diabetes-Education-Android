package edu.emory.diabetes.education.presentation.fragments.browse

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BrowseViewPagerAdapter(
    val fragment: Fragment,
    val size: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int) = when (position) {
        0 -> ContentFragment()
        1 -> NotesFragment()
        else -> BookmarksFragment()
    }
}