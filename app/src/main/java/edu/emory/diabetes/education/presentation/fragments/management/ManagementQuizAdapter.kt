package edu.emory.diabetes.education.presentation.fragments.management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsQuizItemBinding
import edu.emory.diabetes.education.domain.model.Quiz
import edu.emory.diabetes.education.presentation.fragments.management.ManagementQuizDiffUtil.diffUtil

class ManagementQuizAdapter(
    val onEvent: (Quiz) -> Unit
): ListAdapter<Quiz, ManagementQuizAdapter.ManamementQuizViewHolder>(diffUtil) {

    inner class ManamementQuizViewHolder (
        private val bind: FragmentDiabetesBasicsQuizItemBinding
            ): RecyclerView.ViewHolder(bind.root){
                fun bind(quiz: Quiz) = bind.apply {
                    this.quiz = quiz
                }
        init {
            bind.parent.setOnClickListener{onEvent.invoke(currentList[adapterPosition])}
        }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManamementQuizViewHolder {
        return ManamementQuizViewHolder(
            FragmentDiabetesBasicsQuizItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ManamementQuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}