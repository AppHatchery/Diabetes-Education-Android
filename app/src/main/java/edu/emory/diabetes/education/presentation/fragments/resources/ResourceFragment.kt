package edu.emory.diabetes.education.presentation.fragments.resources

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentResourceBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ResourceFragment : BaseFragment(R.layout.fragment_resource) {

    lateinit var mustHaveAdapter: ResourceMustHaveAdapter
    lateinit var foodDiaryAdapter: ResourceFoodDiaryAdapter
    lateinit var communitiesAdapter: ResourcesCommunitiesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentResourceBinding.bind(view)) {
            mustHaveAdapter = ResourceMustHaveAdapter().also {
                it.submitList(ResourceUtil.mustHaveApp)
            }
            foodDiaryAdapter = ResourceFoodDiaryAdapter().also {
                it.submitList(ResourceUtil.foodDiary)
            }
            communitiesAdapter = ResourcesCommunitiesAdapter(
                {}
            ).also {
                it.submitList(ResourceUtil.communities)
            }

            fragment = this@ResourceFragment
        }


    }


    fun toResourceMustHaveFragment(view: View) {
        ResourceFragmentDirections
            .actionResourceFragmentToResourceMustHaveFragment()
            .also { findNavController().navigate(it) }
    }

}