package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentCalculatorInsulinBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorUtils

class InsulinCalculatorFragment: BaseFragment(R.layout.fragment_calculator_insulin) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentCalculatorInsulinBinding.bind(view)) {
            navAdapterSetUp(this)
        }
    }

    private fun navAdapterSetUp(bind: FragmentCalculatorInsulinBinding) = bind.apply {
        adapter = InsulinCalculatorAdapter{
            if (it.id == 0){
                InsulinCalculatorFragmentDirections
                    .actionInsulinCalculatorFragmentToInsulinForFoodFragment(it.id).also {
                        findNavController().navigate(it)
                    }
            }

            if (it.id == 1){
                InsulinCalculatorFragmentDirections
                    .actionInsulinCalculatorFragmentToInsulinForHbsFragment(
                        totalCarbs = "0",
                        carbsRatio = "0",
                        id = it.id
                    ).also {
                        findNavController().navigate(it)
                    }
            }
            if (it.id == 2){
                InsulinCalculatorFragmentDirections
                    .actionInsulinCalculatorFragmentToInsulinForFoodFragment(it.id).also {
                        findNavController().navigate(it)
                    }
            }
        }.apply {
            submitList(CalculatorUtils.calculatorTypes)
        }
    }
}