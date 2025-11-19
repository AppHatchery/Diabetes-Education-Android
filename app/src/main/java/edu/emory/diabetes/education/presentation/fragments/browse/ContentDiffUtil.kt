package edu.emory.diabetes.education.presentation.fragments.browse

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Content

object ContentDiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }
    }
}