package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentInsulinForHbsBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class InsulinForHbsFragment : BaseFragment(R.layout.fragment_insulin_for_hbs) {
    private val args: InsulinForHbsFragmentArgs by navArgs()

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

                        if (bloodSugarNew.text.toString().toFloat() < 150) {
                            bloodSugarError.apply {
                                visibility = View.VISIBLE
                            }
                        } else {
                            bloodSugarError.apply {
                                visibility = View.GONE
                            }
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
                }
            }
        }

    }
}