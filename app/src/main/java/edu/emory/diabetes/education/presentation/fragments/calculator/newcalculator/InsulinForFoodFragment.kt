package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

            carbRatioNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(carbRatioNew.text.toString().isNotEmpty()){
                        carbErrorText.visibility = View.GONE
                        carbRatioText.setTextColor( Color.parseColor("#565656"))
                        carbView.setBackgroundColor(Color.parseColor("#F4EFF9"))
                    }else{
                        carbErrorText.text = "Please enter Carb ratio"
                        carbErrorText.visibility = View.VISIBLE
                        carbRatioText.setTextColor( Color.RED)
                        carbView.setBackgroundColor(Color.RED)
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}
            })

            totalCarbsNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(totalCarbsNew.text.toString().isNotEmpty()){
                        carbErrorText.visibility = View.GONE
                        totalCarbsText.setTextColor( Color.parseColor("#565656"))
                        totalCarbsView.setBackgroundColor(Color.parseColor("#F4EFF9"))
                    }else{
                        carbErrorText.text = "Please enter Total carbs"
                        carbErrorText.visibility = View.VISIBLE
                        totalCarbsText.setTextColor( Color.RED)
                        totalCarbsView.setBackgroundColor(Color.RED)
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}
            })



            val sectionId = args.id

            next.setOnClickListener {
                if (totalCarbsNew.text?.isNotEmpty() == true && carbRatioNew.text?.isNotEmpty() == true) {
                    if (sectionId == 0) {
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
                    } else {
                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToInsulinForHbsFragment(
                                totalCarbsNew.text.toString(),
                                carbRatioNew.text.toString(),
                                sectionId
                            ).also {
                                findNavController().navigate(it)
                            }
                    }
                } else {
                    carbErrorText.apply {
                        when {
                            carbRatioNew.text?.isEmpty() == true -> {
                                visibility = View.VISIBLE
                                carbRatioText.setTextColor(Color.RED)
                                carbView.setBackgroundColor(Color.RED)
                            }
                            totalCarbsNew.text?.isEmpty() == true -> {
                                carbErrorText.text = "Please enter your blood sugar"
                                visibility = View.VISIBLE
                                totalCarbsText.setTextColor(Color.RED)
                                totalCarbsView.setBackgroundColor(Color.RED)
                            }
                            else -> visibility = View.GONE
                        }
                    }
                }

            }
           /* if(sectionId == 0) {
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
                    }else{
                        carbErrorText.apply {
                            when {
                                carbRatioNew.text?.isEmpty() == true -> {
                                    visibility = View.VISIBLE
                                    carbRatioText.setTextColor(Color.RED)
                                    carbView.setBackgroundColor(Color.RED)
                                }
                                totalCarbsNew.text?.isEmpty() == true -> {
                                    carbErrorText.text = "Please enter your blood sugar"
                                    visibility = View.VISIBLE
                                    totalCarbsText.setTextColor(Color.RED)
                                    totalCarbsView.setBackgroundColor(Color.RED)
                                }
                                else -> visibility = View.GONE
                            }
                        }
                    }
                }
            } else{
                next.setOnClickListener {
                    if (totalCarbsNew.text?.isNotEmpty() == true && carbRatioNew.text?.isNotEmpty() == true) {
                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToInsulinForHbsFragment(
                                totalCarbs = totalCarbsNew.text.toString(),
                                carbsRatio = carbRatioNew.text.toString(),
                                id = 0
                            ).also {
                                findNavController().navigate(it)
                            }
                    }else{
                        carbErrorText.apply {
                            when {
                                carbRatioNew.text?.isEmpty() == true -> {
                                    visibility = View.VISIBLE
                                    carbRatioText.setTextColor(Color.RED)
                                    carbView.setBackgroundColor(Color.RED)
                                }
                                totalCarbsNew.text?.isEmpty() == true -> {
                                    carbErrorText.text = "Please enter your blood sugar"
                                    visibility = View.VISIBLE
                                    totalCarbsText.setTextColor(Color.RED)
                                    totalCarbsView.setBackgroundColor(Color.RED)
                                }
                                else -> visibility = View.GONE
                            }
                        }
                    }
                }
            }*/
        }
    }

}
