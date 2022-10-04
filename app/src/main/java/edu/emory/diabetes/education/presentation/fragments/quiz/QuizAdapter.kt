package edu.emory.diabetes.education.presentation.fragments.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.presentation.fragments.quiz.QuizAdapter.ViewHolder

class QuizAdapter(
    val onEven: () -> Unit
) : ListAdapter<Choice, ViewHolder>(QuizDiffUtil.diffUtil) {

    private var currentIndex = QuizUtils.answer.size.minus(1)

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: FragmentQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(choice: Choice) = bind.apply {
            question = choice
            view.apply {
                if (currentIndex == adapterPosition) {
                    setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                } else {
                    setBackgroundResource(R.drawable.shape_rectangle_radius_10px)
                    background.setTint(Color.WHITE)
                }
            }

            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener {
                onEven.invoke()
                QuizUtils.answer.clear()
                QuizUtils.answer.add(QuizUtils.questions[0].choices[adapterPosition].id)
                currentIndex = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentQuizQuestionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}