package edu.emory.diabetes.education

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
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
        context?.startActivity(Intent.createChooser(intent, "Choose Browser"))
    }

    val listOfChapter = listOf(
        ChapterEntity(
            "Diabetes Basics",
            R.color.orange_500,
            R.color.gold_100,
            R.drawable.im_knowledge_ladder
        ),
        ChapterEntity(
            "Nutrition and Carbs Counting",
            R.color.green_200,
            R.color.green_150,
            R.drawable.im_taco_and_avocado_food
        ),
        ChapterEntity(
            "Diabetes Self-Management",
            R.color.pink_300,
            R.color.pink_200,
            R.drawable.im_knowledge_idea
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

}


