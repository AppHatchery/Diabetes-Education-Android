package edu.emory.diabetes.education.presentation.fragments.management.quiz

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Choice

object ManagementQuizDiffUtil {
    val diffUtil = object: DiffUtil.ItemCallback<Choice>(){
        override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
           return oldItem == newItem
        }

    }
}