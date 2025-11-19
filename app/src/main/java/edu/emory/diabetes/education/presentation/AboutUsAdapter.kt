package edu.emory.diabetes.education.presentation


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.emory.diabetes.education.presentation.fragments.aboutus.AboutAsContentFragment
import edu.emory.diabetes.education.presentation.fragments.aboutus.AboutUsTeamFragment

class AboutUsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {

            AboutUsTeamFragment()
        } else {
            AboutAsContentFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}