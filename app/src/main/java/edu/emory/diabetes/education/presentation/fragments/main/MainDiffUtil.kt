package edu.emory.diabetes.education.presentation.fragments.main

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Chapter

object MainDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<Chapter>() {
        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }
    }
}