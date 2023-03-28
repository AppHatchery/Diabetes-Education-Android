package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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
                                totalCarbsNew.text?.clear()
                                carbRatioNew.text?.clear()
                                findNavController().navigate(it)
                            }
                    } else {
                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToInsulinForHbsFragment(
                                totalCarbsNew.text.toString(),
                                carbRatioNew.text.toString(),
                                sectionId
                            ).also {
                                totalCarbsNew.text?.clear()
                                carbRatioNew.text?.clear()
                                findNavController().navigate(it)
                            }
                    }
                } else {
                    handleEmptyFields(this)
                }

            }
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            carbRatioNew.setOnFocusChangeListener { _, hasFocus ->

                if (hasFocus) {
                    carbRatioNew.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(carbRatioNew, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(carbRatioNew.text.isNullOrEmpty())
                    {
                        carbRatioNew.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }

            totalCarbsNew.setOnFocusChangeListener { _, hasFocus ->

                if (hasFocus) {
                    totalCarbsNew.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(totalCarbsNew, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(totalCarbsNew.text.isNullOrEmpty())
                    {
                        totalCarbsNew.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }
        }
    }
    private fun handleEmptyFields(bind: FragmentInsulinForFoodBinding){
        val emptyFields = mutableListOf<String>()

        if(bind.carbRatioNew.text?.isEmpty() == true){
            emptyFields.add("carb Ratio")
            bind.carbRatioText.setTextColor(Color.RED)
            bind.carbView.setBackgroundColor(Color.RED)
        }else{
            bind.carbRatioText.setTextColor(Color.parseColor("#565656"))
            bind.carbView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (bind.totalCarbsNew.text?.isEmpty() == true) {
            emptyFields.add("Blood sugar")
            bind.totalCarbsText.setTextColor(Color.RED)
            bind.totalCarbsView.setBackgroundColor(Color.RED)
        } else {
            bind.totalCarbsText.setTextColor(Color.parseColor("#565656"))
            bind.totalCarbsView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (emptyFields.size > 1) {
            bind.carbErrorText.text = "Please enter missing data."
            bind. carbErrorText.visibility = View.VISIBLE
        } else if (emptyFields.size == 1) {
            bind.carbErrorText.text = "Please enter ${emptyFields[0]}."
            bind.carbErrorText.visibility = View.VISIBLE
        } else {
            bind.carbErrorText.visibility = View.GONE
        }

        bind.carbRatioNew.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                handleEmptyFields(bind)
            }
        })

        bind.totalCarbsNew.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                handleEmptyFields(bind)
            }
        })
    }

}
