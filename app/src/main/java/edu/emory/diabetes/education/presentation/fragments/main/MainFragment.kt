package edu.emory.diabetes.education.presentation.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentMainBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentMainBinding.bind(view)) {
            setupAdapter(this)
            orientation.setOnClickListener {
                MainFragmentDirections
                    .actionMainFragmentToOrientationFragment()
                    .also { findNavController().navigate(it) }
            }
        }

    }

    private fun setupAdapter(bind: FragmentMainBinding) = bind.apply {
        topAdapter = MainAdapter {
            if (it.id == 1) {
                MainFragmentDirections
                    .actionMainFragmentToDiabetesBasicsFragment().also {
                        findNavController().navigate(it)
                    }
            }
        }.apply {
            viewModel.state
                .onEach {
                    if (it.data.isNotEmpty())
                        submitList(it.data.subList(0, 2))
                }
                .launchIn(lifecycleScope)
        }


        bottomAdapter = MainAdapter {
            MainFragmentDirections
                .actionMainFragmentToBrowseFragment().apply {
                    findNavController().navigate(this)
                }
        }.apply {
            viewModel
                .state.onEach {
                    if (it.data.isNotEmpty())
                        submitList(it.data.subList(2, 3))
                }
                .launchIn(lifecycleScope)
        }

    }
}

