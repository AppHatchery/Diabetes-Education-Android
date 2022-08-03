package edu.emory.diabetes.education.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment(layoutRes) {

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

}