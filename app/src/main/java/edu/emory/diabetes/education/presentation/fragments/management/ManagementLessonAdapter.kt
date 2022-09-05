package edu.emory.diabetes.education.presentation.fragments.management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonItemBinding
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.presentation.fragments.management.ManagementLessonDiffUtil.diffUtil

class ManagementLessonAdapter(
    val onEvent: (Lesson) -> Unit
):ListAdapter<Lesson, ManagementLessonAdapter.ManagementLessonViewHolder>(diffUtil) {

    inner class ManagementLessonViewHolder(
        private val bind: FragmentDiabetesBasicsLessonItemBinding
    ): RecyclerView.ViewHolder(bind.root){

        fun bind(lesson: Lesson) = bind.apply {
            this.lesson = lesson
        }
        init {
            bind.cardView.setOnClickListener { onEvent.invoke(currentList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagementLessonViewHolder {
        return ManagementLessonViewHolder(
            FragmentDiabetesBasicsLessonItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ManagementLessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}