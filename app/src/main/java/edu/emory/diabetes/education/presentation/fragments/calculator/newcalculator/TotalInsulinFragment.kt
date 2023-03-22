package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentTotalInsulinBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorEvent
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorUtils
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

class TotalInsulinFragment: BaseFragment(R.layout.fragment_total_insulin) {
    private val viewModel: CalculatorViewModel by viewModels()
    private val args: TotalInsulinFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentTotalInsulinBinding.bind(view)){
            //insulin for food and high blood sugar adapter
            bottomAdapter = TotalInsulinAdapter().also { adapter ->
                viewModel.getInsulinData.onEach {
                    adapter.submitList(it)
                }.launchIn(lifecycleScope)
            }
            //total insulin adapter
            topAdapter = TotalInsulinAdapter().also  { adapter ->
                viewModel.getInsulinData.onEach {
                    adapter.submitList(it.subList(2, 3))
                }.launchIn(lifecycleScope)
            }

            // nav arguments
            val totalCarbs = args.totalCarbs
            val carbRatio = args.carbsRatio
            val correctionFactor = args.correctionFactor
            val bloodSugar = args.bloodSugar
            val targetBloodSugar = args.targetBloodSugar

            var insulinFood: Float = 0.0F
            var insulinHbs: Float = 0.0F
            var totalInsulin: Float = 0.0F

            //insulin for food calculation
            if (carbRatio.toString().toFloat() != 0.0F){
                insulinFood = totalCarbs.toString().toFloat().div(carbRatio.toString().toFloat())
                val insulinCalculator = CalculatorUtils.data[0].copy(
                    answer = DecimalFormat("#.#").format(insulinFood)
                )
                viewModel.onEvent(CalculatorEvent.CalculateInsulinForFood(insulinCalculator.toInsulinCalculator()))
            }else{
                insulinFood = 0.0F
                val insulinFoodtest = CalculatorUtils.data[0].copy(answer = "--")
                viewModel.onEvent(CalculatorEvent.CalculateInsulinForBloodSugar(insulinFoodtest.toInsulinCalculator()))
            }

            //insulin for high blood sugar calculation
            if (correctionFactor.toString().toFloat() != 0.0F){
                insulinHbs = (
                        bloodSugar.toString().toFloat() - targetBloodSugar.toString().toFloat())
                    .div(correctionFactor.toString().toFloat())
                val insulinHbsCalculator = CalculatorUtils.data[1].copy(
                    answer = DecimalFormat("#.#").format(insulinHbs)
                )
                viewModel.onEvent(CalculatorEvent.CalculateInsulinForBloodSugar(insulinHbsCalculator.toInsulinCalculator()))
            }else if(bloodSugar.toString().toFloat() < 150){
                insulinHbs = 0.0F
                val insulinHbsCalculator = CalculatorUtils.data[1].copy(
                    answer = DecimalFormat("#.#").format(insulinHbs)
                )
                viewModel.onEvent(CalculatorEvent.CalculateInsulinForBloodSugar(insulinHbsCalculator.toInsulinCalculator()))
            }else{
                insulinHbs = 0.0F
                val insulinHbsTest = CalculatorUtils.data[1].copy(answer = "--")
                viewModel.onEvent(CalculatorEvent.CalculateInsulinForBloodSugar(insulinHbsTest.toInsulinCalculator()))
            }

            //total insulin calculation
            totalInsulin = insulinFood + insulinHbs
            val  totalInsulinCalculator = CalculatorUtils.data[2].copy(
                answer = DecimalFormat("#.#").format(totalInsulin)
            )
            viewModel.onEvent(CalculatorEvent.CalculateTotalInsulin(totalInsulinCalculator.toInsulinCalculator()))


            newCalculator.setOnClickListener {
                TotalInsulinFragmentDirections
                    .actionTotalInsulinFragmentToInsulinCalculatorFragment().also {
                        findNavController().navigate(it)
                    }
            }

        }
    }


}