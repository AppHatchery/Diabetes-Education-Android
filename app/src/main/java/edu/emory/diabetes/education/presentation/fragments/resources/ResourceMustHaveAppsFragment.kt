package edu.emory.diabetes.education.presentation.fragments.resources

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentResourceMustHaveAppsBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ResourceMustHaveAppsFragment : BaseFragment(R.layout.fragment_resource_must_have_apps) {

    lateinit var mustHaveRecyclerAdapter: MustHaveRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(FragmentResourceMustHaveAppsBinding.bind(view)){

            mustHaveRecyclerAdapter = MustHaveRecyclerAdapter().also {
                it.submitList(ResourceUtil.mustHaveAppPage)
            }

            mustHaveFragment = this@ResourceMustHaveAppsFragment
        }
    }
}