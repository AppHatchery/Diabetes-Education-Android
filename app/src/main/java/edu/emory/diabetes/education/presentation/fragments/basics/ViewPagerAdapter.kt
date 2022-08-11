package edu.emory.diabetes.education.presentation.fragments.basics

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 ->
                LessonFragment()

            else -> QuizFragment()
        }

        return fragment
    }
}