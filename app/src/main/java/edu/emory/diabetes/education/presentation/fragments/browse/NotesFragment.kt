package edu.emory.diabetes.education.presentation.fragments.browse

import BrowseUtils
import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentNotesBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class NotesFragment : BaseFragment(R.layout.fragment_notes) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentNotesBinding.bind(view)) {
            adapter = NotesAdapter().apply {
                submitList(BrowseUtils.notesData.toList())

            }

        }
    }
}