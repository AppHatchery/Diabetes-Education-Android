package edu.emory.diabetes.education.presentation.fragments.calculator

import androidx.recyclerview.widget.DiffUtil

object InsulinDialogDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<Int>() {
        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}