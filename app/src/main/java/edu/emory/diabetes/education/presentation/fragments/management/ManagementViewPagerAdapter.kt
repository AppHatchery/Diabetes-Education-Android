package edu.emory.diabetes.education.presentation.fragments.management

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ManagementViewPagerAdapter(
    val fragment: Fragment,
    val size: Int
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int) = when (position) {
        0 -> ManagementLessonFragment(fragment)
        else -> ManagementQuizFragment(fragment)
    }
}