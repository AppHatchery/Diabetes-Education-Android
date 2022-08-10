package edu.emory.diabetes.education.presentation.fragments.diabetesBasics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentCalculatorItemBinding
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonItemBinding
import edu.emory.diabetes.education.domain.model.InsulinCalculator
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorAdapter

class LessonAdapter: ListAdapter<Lesson, LessonAdapter.LessonViewHolder>(LessonDiffUtil.diffUtil) {
    
    inner class LessonViewHolder(
        private val bind:FragmentDiabetesBasicsLessonItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        
        fun bind(lesson: Lesson) = bind.apply {
            this.lesson = lesson
        }
        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            FragmentDiabetesBasicsLessonItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




}