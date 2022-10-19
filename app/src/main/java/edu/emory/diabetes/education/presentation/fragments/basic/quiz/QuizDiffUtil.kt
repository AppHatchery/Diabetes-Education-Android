package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Choice

object QuizDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<Choice>() {
        override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem.id == newItem.id
        }
    }
}