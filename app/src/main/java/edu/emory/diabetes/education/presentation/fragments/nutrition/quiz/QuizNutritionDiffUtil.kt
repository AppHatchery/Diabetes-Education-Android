package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Choice

object QuizNutritionDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<Choice>(){
        override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem == newItem
        }

    }
}