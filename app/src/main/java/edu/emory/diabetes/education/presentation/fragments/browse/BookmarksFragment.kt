package edu.emory.diabetes.education.presentation.fragments.browse

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentBookmarksBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class BookmarksFragment: BaseFragment(R.layout.fragment_bookmarks) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(FragmentBookmarksBinding.bind(view)){
            adapter = BookmarkAdapter().apply {
                submitList(BrowseUtils.bookMarkData.toList())
            }
        }

    }
}