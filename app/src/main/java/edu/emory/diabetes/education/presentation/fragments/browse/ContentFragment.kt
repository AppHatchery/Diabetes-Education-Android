package edu.emory.diabetes.education.presentation.fragments.browse

import BrowseUtils
import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentContentBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ContentFragment : BaseFragment(R.layout.fragment_content) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentContentBinding.bind(view)) {
            adapter = ContentAdapter().apply {
                submitList(BrowseUtils.contentData.toList())
            }

            selfMgmtRV.adapter = ContentAdapter().apply {
                submitList(BrowseUtils.selfManagementData.toList())
            }

        }

    }


}
