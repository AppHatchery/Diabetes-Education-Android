package edu.emory.diabetes.education.presentation.fragments.resources

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentResourceBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ResourceFragment: BaseFragment(R.layout.fragment_resource) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentResourceBinding.bind(view)){
        }
    }
}