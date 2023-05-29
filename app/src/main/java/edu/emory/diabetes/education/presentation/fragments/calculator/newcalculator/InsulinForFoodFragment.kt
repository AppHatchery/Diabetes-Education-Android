package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.View.OnTouchListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.internal.ViewUtils.dpToPx
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentInsulinForFoodBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import sdk.pendo.io.Pendo

class InsulinForFoodFragment : BaseFragment(R.layout.fragment_insulin_for_food) {
    private val args: InsulinForFoodFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentInsulinForFoodBinding.bind(view)) {
            val sectionId = args.id
            val context = root.context
            val marginBottom = dpToPx(context, 16)

            val editTextList = mutableListOf<EditText>()
            editTextList.add(totalCarbsNew)
            editTextList.add(carbRatioNew)

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


            val bottomBar = activity?.findViewById<View>(R.id.bottomNavigationView)

            activity?.window?.decorView?.setOnApplyWindowInsetsListener { view, insets ->
                val insetsCompat = toWindowInsetsCompat(insets, view)
                if (bottomBar != null) {
                    bottomBar.isGone = insetsCompat.isVisible(ime())
                }
                view.onApplyWindowInsets(insets)
            }


            next.setOnClickListener {
                if (totalCarbsNew.text?.isNotEmpty() == true && carbRatioNew.text?.isNotEmpty() == true) {
                    //hide keyboard
                    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                    //pendo tracking
                    val properties = hashMapOf<String, Any>()
                    properties["carbs"] = totalCarbsNew.text.toString()
                    properties["ratio"] = carbRatioNew.text.toString()
                    Pendo.track("Calculate_insulin_for_food", properties)
                    if (sectionId == 0) {
                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToTotalInsulinFragment(
                                totalCarbs = totalCarbsNew.text.toString(),
                                carbsRatio = carbRatioNew.text.toString(),
                                correctionFactor = "0",
                                bloodSugar = "0",
                                targetBloodSugar = "0",
                                id = sectionId
                            ).also {
                                findNavController().navigate(it)
                            }
                    } else {
                        InsulinForFoodFragmentDirections
                            .actionInsulinForFoodFragmentToInsulinForHbsFragment(
                                totalCarbs = totalCarbsNew.text.toString(),
                                carbsRatio = carbRatioNew.text.toString(),
                                id = sectionId
                            ).also {
                                findNavController().navigate(it)
                            }
                    }
                } else {
                    handleEmptyFields(this)
                }

            }
            val inputMethodManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            carbRatioNew.setOnFocusChangeListener { _, hasFocus ->

                if (hasFocus) {
                    carbRatioNew.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(carbRatioNew, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    if (carbRatioNew.text.isNullOrEmpty()) {
                        carbRatioNew.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }

            totalCarbsNew.setOnFocusChangeListener { _, hasFocus ->

                if (hasFocus) {
                    totalCarbsNew.setHintTextColor(Color.TRANSPARENT)
                    inputMethodManager.showSoftInput(
                        totalCarbsNew,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                } else {
                    if (totalCarbsNew.text.isNullOrEmpty()) {
                        totalCarbsNew.setHintTextColor(Color.parseColor("#e9e9e9"))
                    }
                }
            }
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    private fun handleEmptyFields(bind: FragmentInsulinForFoodBinding) {
        val emptyFields = mutableListOf<String>()

        if (bind.carbRatioNew.text?.isEmpty() == true) {
            emptyFields.add("carb Ratio")
            bind.carbRatioText.setTextColor(Color.RED)
            bind.carbView.setBackgroundColor(Color.RED)
        } else {
            bind.carbRatioText.setTextColor(Color.parseColor("#565656"))
            bind.carbView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (bind.totalCarbsNew.text?.isEmpty() == true) {
            emptyFields.add("Total Carbs")
            bind.totalCarbsText.setTextColor(Color.RED)
            bind.totalCarbsView.setBackgroundColor(Color.RED)
        } else {
            bind.totalCarbsText.setTextColor(Color.parseColor("#565656"))
            bind.totalCarbsView.setBackgroundColor(Color.parseColor("#F4EFF9"))
        }
        if (emptyFields.size > 1) {
            bind.carbErrorText.text = "Please enter missing data."
            bind.carbErrorText.visibility = View.VISIBLE
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
