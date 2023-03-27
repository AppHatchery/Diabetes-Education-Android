package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentInsulinForHbsBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class InsulinForHbsFragment : BaseFragment(R.layout.fragment_insulin_for_hbs) {
    private val args: InsulinForHbsFragmentArgs by navArgs()
    private val HINT_EMPTY = ""
    private val HINT_DEFAULT_bloodSugarNew = "160"
    private val HINT_DEFAULT_targetBloodSugar = "100"
    private val HINT_DEFAULT_correctionFactor = "2"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentInsulinForHbsBinding.bind(view)) {
            val totalCarbs = args.totalCarbs
            val carbRatio = args.carbsRatio
            val id = args.id

            if (id == 0) {
                stepOne.text = getString(R.string.step_two)
            } else {
                stepOne.text = getString(R.string.step_one)
            }

            bloodSugarNew.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (bloodSugarNew.text.toString().isNotEmpty()) {
                        bloodSugarError.apply {
                            visibility = if (bloodSugarNew.text.toString().toFloat() < 150) View.VISIBLE else View.GONE
                        }
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
            })

            next.setOnClickListener {
                if (correctionFactor.text?.isNotEmpty() == true && bloodSugarNew.text?.isNotEmpty() == true && targetBloodSugar.text?.isNotEmpty() == true) {
                    InsulinForHbsFragmentDirections
                        .actionInsulinForHbsFragmentToTotalInsulinFragment(
                            correctionFactor = correctionFactor.text.toString(),
                            bloodSugar = bloodSugarNew.text.toString(),
                            targetBloodSugar = targetBloodSugar.text.toString(),
                            totalCarbs = totalCarbs.toString(),
                            carbsRatio = carbRatio.toString()
                        ).also {
                            findNavController().navigate(it)
                        }
                } else {
                    handleEmptyFields(this)
                }
            }
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            bloodSugarNew.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    bloodSugarNew.hint = HINT_EMPTY
                    inputMethodManager.showSoftInput(bloodSugarNew, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(bloodSugarNew.text.isNullOrEmpty())
                    {
                        bloodSugarNew.hint = HINT_DEFAULT_bloodSugarNew
                    }
                }
            }

            targetBloodSugar.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    targetBloodSugar.hint = HINT_EMPTY
                    inputMethodManager.showSoftInput(targetBloodSugar, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(targetBloodSugar.text.isNullOrEmpty())
                    {
                        targetBloodSugar.hint = HINT_DEFAULT_targetBloodSugar
                    }
                }
            }


            correctionFactor.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    correctionFactor.hint = HINT_EMPTY
                    inputMethodManager.showSoftInput(correctionFactor, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(correctionFactor.text.isNullOrEmpty())
                    {
                        correctionFactor.hint = HINT_DEFAULT_correctionFactor
                    }
                }
            }
        }
    }

    private fun handleEmptyFields(bind: FragmentInsulinForHbsBinding) {
        val emptyFields = mutableListOf<String>()
        if (bind.correctionFactor.text?.isEmpty() == true) {
            emptyFields.add("Correction factor")
            bind.correctionFactorText.setTextColor(Color.RED)
            bind.correctionView.setBackgroundColor(Color.RED)
        } else {
            bind.correctionFactorText.setTextColor(Color.parseColor("#565656"))
            bind.correctionView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (bind.bloodSugarNew.text?.isEmpty() == true) {
            emptyFields.add("Blood sugar")
            bind.bloodSugarText.setTextColor(Color.RED)
            bind.bloodSugarView.setBackgroundColor(Color.RED)
        } else {
            bind.bloodSugarText.setTextColor(Color.parseColor("#565656"))
            bind.bloodSugarView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (bind.targetBloodSugar.text?.isEmpty() == true) {
            emptyFields.add("Target blood sugar")
            bind.targetBloodText.setTextColor(Color.RED)
            bind.targetBloodView.setBackgroundColor(Color.RED)
        } else {
            bind.targetBloodText.setTextColor(Color.parseColor("#565656"))
            bind.targetBloodView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (emptyFields.size > 1) {
            bind.errorText.text = "Please enter missing data."
            bind. errorText.visibility = View.VISIBLE
        } else if (emptyFields.size == 1) {
            bind.errorText.text = "Please enter ${emptyFields[0]}."
            bind.errorText.visibility = View.VISIBLE
        } else {
            bind.errorText.visibility = View.GONE
        }

        bind.correctionFactor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                handleEmptyFields(bind)
            }
        })

        bind.bloodSugarNew.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                handleEmptyFields(bind)
            }
        })

        bind.targetBloodSugar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                handleEmptyFields(bind)
            }
        })
    }


}