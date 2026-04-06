package edu.emory.diabetes.education.presentation.fragments.newResources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesNavigation

class NewResourcesFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val fragmentNavController = findNavController()
                NewResourcesNavigation(
                    onExitToMain = {
                        fragmentNavController.popBackStack()
                    }
                )
            }
        }
    }
}