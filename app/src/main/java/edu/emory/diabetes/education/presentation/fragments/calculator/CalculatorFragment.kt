package edu.emory.diabetes.education.presentation.fragments.calculator

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils.dialog
import edu.emory.diabetes.education.Utils.dialogShow
import edu.emory.diabetes.education.databinding.FragmentCalculatorBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorEvent.CalculateInsulinForFood
import edu.emory.diabetes.education.presentation.fragments.calculator.InsulinDialogAdapter.Companion.currentPosition
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

class CalculatorFragment : BaseFragment(R.layout.fragment_calculator) {

    private val viewModel: CalculatorViewModel by viewModels()
    private var correctionFactor: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentCalculatorBinding.bind(view)) {

            adapter = CalculatorAdapter().also { adapter ->
                viewModel.getInsulinData.onEach {
                    adapter.submitList(it)
                }.launchIn(lifecycleScope)
            }

            recyclerView.layoutManager = GridLayoutManager(
                requireContext(),
                1,
                RecyclerView.HORIZONTAL,
                false
            )


            carbsRatio.setOnClickListener {
                with(Dialog(requireContext()).dialog()) {
                    setContentView(R.layout.fragment_calculator_dialog)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerView.adapter = InsulinDialogAdapter {
                        carbsRatio.text = it.toString()
                        currentPosition = it
                        dismiss()
                    }.also {
                        it.submitList((1 until 16).toList()) {
                            recyclerView.scrollToPosition(currentPosition.minus(1))
                        }
                    }
                    dialogShow()
                }
            }



            appCompatButton.setOnClickListener {

                if (totalCarbs.text?.isNotEmpty() == true) {

                    val insulinFood = totalCarbs.text.toString().toFloat()
                        .div(carbsRatio.text.toString().toFloat())

                    val insulinCalculator = CalculatorUtils.data[0].copy(
                        answer = DecimalFormat("#.###").format(insulinFood)
                    )

                    Log.e("TAG", "onViewCreated: " + insulinCalculator )

                    viewModel.onEvent(CalculateInsulinForFood(insulinCalculator.toInsulinCalculator()))
                }

                if(bloodSugar.text?.isNotEmpty() == true && correctionFactor > 0){
                    val insulinBloodSugar = (bloodSugar.text.toString().toFloat()
                        .minus(100)).div(correctionFactor)

                    val insulinBloodCalculator = CalculatorUtils.data[1].copy(
                        answer = DecimalFormat("#.##").format(insulinBloodSugar)
                    )

                    Log.e("TAG", "onViewCreated: " + insulinBloodCalculator )
                    viewModel.onEvent(CalculatorEvent.CalculateInsulinForBloodSugar(insulinBloodCalculator.toInsulinCalculator()))
                }


            }

            val correctionFactorArray = resources.getStringArray(R.array.correction_factor_array)
                    val spinner: Spinner = correctionFactorSpinner
            if (spinner != null) {
                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.correction_factor_array,
                    R.layout.spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            p3: Long
                        ) {
                            correctionFactor = correctionFactorArray[position].toInt()
                            Log.e("TAG", "onItemSelected: " + correctionFactor)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                }
            }

            }




        }

}

