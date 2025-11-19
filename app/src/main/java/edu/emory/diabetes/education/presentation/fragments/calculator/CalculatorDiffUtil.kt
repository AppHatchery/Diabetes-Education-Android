package edu.emory.diabetes.education.presentation.fragments.calculator

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.InsulinCalculator

object CalculatorDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<InsulinCalculator>() {
        override fun areContentsTheSame(
            oldItem: InsulinCalculator,
            newItem: InsulinCalculator
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: InsulinCalculator,
            newItem: InsulinCalculator
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}