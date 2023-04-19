package edu.emory.diabetes.education

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
import edu.emory.diabetes.education.data.local.entities.ChapterSearchEntity
import java.util.*

object Utils {


    fun Dialog.dialog(): Dialog {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)).also { return this }
    }

    fun Dialog.dialogShow() {
        Objects.requireNonNull(window!!).setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        show()
    }


    fun launchUrl(context: Context, url: String) {
        val browserUrl = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, browserUrl)
        context.startActivity(Intent.createChooser(intent, "Choose Browser"))
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    val listOfChapter = listOf(
        ChapterEntity(
            "Diabetes Basics",
            R.color.orange_500,
            R.color.gold_100,
            R.drawable.im_knowledge_ladder,
            1,
        ),
        ChapterEntity(
            "Nutrition and Carbs Counting",
            R.color.green_200,
            R.color.green_150,
            R.drawable.im_taco_and_avocado_food,
            2,
        ),
        ChapterEntity(
            "Diabetes Self-Management",
            R.color.pink_300,
            R.color.pink_200,
            R.drawable.im_knowledge_idea,
            3
        ),

        )


    val chapterContent = listOf(
        ChapterSearchEntity(
            0,
            "what is diabetes",
            "Signs of diabetes occur because the body lacks insulin. This causes blood sugar to build up in the blood leading to these signs"

        )
    )

    fun EditText.setOnTextWatcher(
        onTextChangedListener: (String) -> Unit
    ) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                charSequence?.let {
                    if (it.isNotBlank()) onTextChangedListener(it.toString().lowercase().trim())
                }

            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    @SuppressLint("RestrictedApi")
    fun setSupportActionBar(
        activity: AppCompatActivity,
        toolbar: Toolbar
    ) = activity.apply {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDefaultDisplayHomeAsUpEnabled(true)
            it.title = null
        }
        toolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }


    fun AppCompatEditText.onSearch(callback: () -> Unit) {
        setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callback.invoke()
                view.hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    //Quiz answer utilities
    fun hasAllAnswers(submittedAnswers: List<String>,answers: List<String>): Boolean {
        if (submittedAnswers.size>answers.size){
            return false
        }else{
            for (element in answers) {
                if (!submittedAnswers.contains(element)) {
                    return false
                }
            }
            return true
        }
    }
    fun hasSomeAnswers(submittedAnswers: List<String>,answers: List<String>,maximumAnswers:Int):Boolean{
        val found=answers.intersect(submittedAnswers.toSet())
        return found.isNotEmpty()
    }
    enum class RESULTS_ON_SUBMIT {
        HAS_ALL_CORRECT,
        HAS_SOME_CORRECT,
        HAS_NONE_CORRECT,
    }
    interface OnSubmitResultStateListener{
        fun onSubmitResultState(resultInfo:RESULTS_ON_SUBMIT,answerChoice:String,hasSomeAllCorrect:Boolean)
    }


}


