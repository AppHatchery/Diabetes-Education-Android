package edu.emory.diabetes.education.presentation.fragments.nutrition


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsQuizItemBinding
import edu.emory.diabetes.education.domain.model.Quiz

class NutritionQuizAdapter(
    val onEvent: (Quiz) -> Unit
) : ListAdapter<Quiz, NutritionQuizAdapter.QuizViewHolder>(NutritionQuizDiffUtil.diffUtil) {

    inner class QuizViewHolder(
        private val bind: FragmentDiabetesBasicsQuizItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bind(quiz: Quiz) = bind.apply {
            this.quiz = quiz
        }

        init {
            bind.root.setOnClickListener { onEvent.invoke(currentList[adapterPosition]) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            FragmentDiabetesBasicsQuizItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}