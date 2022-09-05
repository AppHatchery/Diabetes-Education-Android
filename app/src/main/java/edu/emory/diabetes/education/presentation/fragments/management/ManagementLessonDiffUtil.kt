package edu.emory.diabetes.education.presentation.fragments.management

import androidx.recyclerview.widget.DiffUtil
import edu.emory.diabetes.education.domain.model.Lesson

object ManagementLessonDiffUtil {
    val diffUtil= object: DiffUtil.ItemCallback<Lesson>(){
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }
    }
}