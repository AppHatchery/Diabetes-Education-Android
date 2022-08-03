package edu.emory.diabetes.education.presentation.fragments.calculator

import android.app.Dialog
import android.os.Bundle
import android.view.View
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

                    viewModel.onEvent(CalculateInsulinForFood(insulinCalculator.toInsulinCalculator()))
                }
            }


        }

    }
}
