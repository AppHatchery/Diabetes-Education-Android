package edu.emory.diabetes.education.presentation.fragments.quiz

import androidx.recyclerview.widget.DiffUtil

object QuizDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}