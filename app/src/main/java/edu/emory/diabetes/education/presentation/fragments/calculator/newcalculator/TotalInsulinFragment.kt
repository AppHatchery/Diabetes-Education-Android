package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
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
import sdk.pendo.io.Pendo
import java.text.DecimalFormat

class TotalInsulinFragment : BaseFragment(R.layout.fragment_total_insulin) {
    private val args: TotalInsulinFragmentArgs by navArgs()
    val viewModel: InsulinCalculatorViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentTotalInsulinBinding.bind(view)) {
            // nav arguments
            val totalCarbs = args.totalCarbs
            val carbRatio = args.carbsRatio
            val correctionFactor = args.correctionFactor
            val bloodSugar = args.bloodSugar
            val targetBloodSugar = args.targetBloodSugar
            val theId = args.id

            var insulinFood: Float = 0.0F
            var insulinHbs: Float = 0.0F
            var totalInsulin: Float = 0.0F

            //Insulin for food
            if (carbRatio.toString().toFloat() != 0.0F && totalCarbs.toString().toFloat() != 0.0F) {
                insulinFood = totalCarbs.toString().toFloat().div(carbRatio.toString().toFloat())
                insulinFoodAnswer.text = DecimalFormat("#.#").format(insulinFood)
            } else {
                insulinFoodAnswer.text = "--"
            }

            //Insulin for HBS
            if (correctionFactor.toString().toFloat() != 0.0F) {
                insulinHbs = (
                        bloodSugar.toString().toFloat() - targetBloodSugar.toString().toFloat())
                    .div(correctionFactor.toString().toFloat())
                insulinHbsAnswer.text = DecimalFormat("#.#").format(insulinHbs)
            }

            if (theId == 0){
               insulinHbsAnswer.text = "--"
            }else if(bloodSugar.toString().toFloat() < 150){
                insulinHbs = 0.0F
                insulinHbsAnswer.text = DecimalFormat("#.#").format(insulinHbs)
            }

            //Total insulin
            totalInsulin = insulinFood + insulinHbs
            totalInsulinAnswer.text = DecimalFormat("#.#").format(totalInsulin)

            //pendo tracking
            val properties = hashMapOf<String, Any>()
            properties["for_food"] = insulinFood
            properties["for_hbs"] = insulinHbs
            properties["total"] = totalInsulin
            Pendo.track("Calculator_results", properties)

            //new calculator button
            newCalculator.setOnClickListener {
                viewModel.clearData()
                TotalInsulinFragmentDirections
                findNavController().popBackStack(R.id.insulinCalculatorFragment, false)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        view?.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right))
    }

}
