package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentInsulinForFoodBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class InsulinForFoodFragment: BaseFragment(R.layout.fragment_insulin_for_food) {
    private val args: InsulinForFoodFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentInsulinForFoodBinding.bind(view)){
            val sectionId = args.id
            if(sectionId == 0) {
                next.setOnClickListener {
                    if (totalCarbsNew.text?.isNotEmpty() == true && carbRatioNew.text?.isNotEmpty() == true) {

                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToTotalInsulinFragment(
                                totalCarbsNew.text.toString(),
                                carbRatioNew.text.toString(),
                                correctionFactor = "0",
                                bloodSugar = "0",
                                targetBloodSugar = "0"
                            ).also {
                                findNavController().navigate(it)
                            }
                    }
                }
            } else{
                next.setOnClickListener {
                    InsulinForFoodFragmentDirections
                        .actionInsulinForFoodFragmentToInsulinForHbsFragment(
                            totalCarbs = totalCarbsNew.text.toString(),
                            carbsRatio = carbRatioNew.text.toString(),
                            id = 0
                        ).also {
                            findNavController().navigate(it)
                        }
                }
            }
        }
    }

}