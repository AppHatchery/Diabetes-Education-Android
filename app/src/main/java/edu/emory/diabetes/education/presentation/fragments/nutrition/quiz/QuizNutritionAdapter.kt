package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.NutritionQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Choice


class QuizNutritionAdapter(
    val onEvent: () -> Unit
): ListAdapter<Choice, QuizNutritionAdapter.QuizNutritionViewHolder>(QuizNutritionDiffUtil.diffUtil) {

    private var currentIndex = QuizNutritionUtil.answer.size.minus(1)

    @SuppressLint("NotifyDataSetChanged")
    inner class QuizNutritionViewHolder(
        private val bind: NutritionQuizQuestionItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(choice: Choice) = bind.apply {
            question = choice
            view.apply {
                if (currentIndex == adapterPosition){
                    setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                }else{
                    setBackgroundResource(R.drawable.shape_rectangle_radius_10px)
                    background.setTint(Color.WHITE)
                }
            }
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener {
                onEvent.invoke()
                QuizNutritionUtil.answer.clear()
                QuizNutritionUtil.answer.add(QuizNutritionUtil.questions[0].choices[adapterPosition].id)
                currentIndex = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizNutritionViewHolder {
        return QuizNutritionViewHolder(
            NutritionQuizQuestionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: QuizNutritionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}