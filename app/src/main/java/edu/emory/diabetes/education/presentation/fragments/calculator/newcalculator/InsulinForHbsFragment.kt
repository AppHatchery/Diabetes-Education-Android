package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentInsulinForHbsBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import sdk.pendo.io.Pendo

class InsulinForHbsFragment : BaseFragment(R.layout.fragment_insulin_for_hbs) {
    private val args: InsulinForHbsFragmentArgs by navArgs()
    val viewModel: InsulinCalculatorViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentInsulinForHbsBinding.bind(view)) {
            //scrolling up when keyboard is shown
            val editTextList = mutableListOf<EditText>()
            editTextList.add(bloodSugarNew)
            editTextList.add(targetBloodSugar)
            editTextList.add(correctionFactor)

            for (editText in editTextList) {
                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        scrollView.smoothScrollTo(0, editText.bottom)
                    }
                }
            }

            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                val r = Rect()
                scrollView.getWindowVisibleDisplayFrame(r)
                val screenHeight = scrollView.rootView.height
                val keypadHeight = screenHeight - r.bottom
                if (keypadHeight > screenHeight * 0.15) {
                    val currentFocus = activity?.currentFocus
                    if (currentFocus is EditText) {
                        scrollView.smoothScrollTo(0, scrollView.bottom)
                    }
                }
            }
            scrollView.viewTreeObserver.addOnGlobalLayoutListener(listener)

            //hiding bottom nav bar when keyboard is shown
            val bottomBar = activity?.findViewById<View>(R.id.bottomNavigationView)

            activity?.window?.decorView?.setOnApplyWindowInsetsListener { view, insets ->
                val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets, view)
                if (bottomBar != null) {
                    bottomBar.isGone = insetsCompat.isVisible(WindowInsetsCompat.Type.ime())
                }
                view.onApplyWindowInsets(insets)
            }

            // nav args
            val totalCarbs = args.totalCarbs
            val carbRatio = args.carbsRatio
            val id = args.id

            //set up saved correction factor
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.correctionFactorState.collect { factor ->
                    if (factor.isNotEmpty()){
                        correctionFactor.setText(factor)
                    }
                }
            }

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
                    //save correction factor if changed
                    viewModel.saveCorrectionFactor(correctionFactor.text.toString())
                    //hide keyboard
                    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    //passing data to viewModel
                    viewModel.correctionFactor = correctionFactor.text.toString()
                    viewModel.bloodSugar = bloodSugarNew.text.toString()
                    viewModel.targetBloodSugar = targetBloodSugar.text.toString()
                    //pendo tracking
                    val properties = hashMapOf<String, Any>()
                    properties["correction_factor"] = correctionFactor.text.toString()
                    properties["blood_sugar"] = bloodSugarNew.text.toString()
                    properties["target_blood_sugar"] = targetBloodSugar.text.toString()
                    properties["carb"] = totalCarbs.toString()
                    properties["ratio"] = carbRatio.toString()
                    Pendo.track("Calculate_insulin_for_hbs", properties)
                    InsulinForHbsFragmentDirections
                        .actionInsulinForHbsFragmentToTotalInsulinFragment(
                            correctionFactor = correctionFactor.text.toString(),
                            bloodSugar = bloodSugarNew.text.toString(),
                            targetBloodSugar = targetBloodSugar.text.toString(),
                            totalCarbs = totalCarbs.toString(),
                            carbsRatio = carbRatio.toString(),
                            id = 1
                        ).also {
                            findNavController().navigate(it)
                        }
                } else {
                    handleEmptyFields(this)
                }
            }

            correctionFactor.setText(viewModel.correctionFactorState.value)
            bloodSugarNew.setText(viewModel.bloodSugar)
            targetBloodSugar.setText(viewModel.targetBloodSugar)

            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            bloodSugarNew.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    bloodSugarNew.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(bloodSugarNew, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(bloodSugarNew.text.isNullOrEmpty())
                    {
                        bloodSugarNew.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }

            targetBloodSugar.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    targetBloodSugar.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(targetBloodSugar, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(targetBloodSugar.text.isNullOrEmpty())
                    {
                        targetBloodSugar.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }

            correctionFactor.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    correctionFactor.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(correctionFactor, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if(correctionFactor.text.isNullOrEmpty())
                    {
                        correctionFactor.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
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