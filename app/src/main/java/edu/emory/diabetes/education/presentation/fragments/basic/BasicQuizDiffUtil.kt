package edu.emory.diabetes.education.presentation.fragments.basic

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Quiz

object BasicQuizDiffUtil{
    val diffUtil = object : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem:Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }
    }
}