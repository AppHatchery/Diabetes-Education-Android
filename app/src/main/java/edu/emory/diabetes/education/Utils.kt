package edu.emory.diabetes.education

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
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
}


