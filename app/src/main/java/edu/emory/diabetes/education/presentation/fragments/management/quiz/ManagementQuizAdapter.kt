package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.ManagementQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Choice

class ManagementQuizAdapter(
    val onEvent: () -> Unit
): ListAdapter<Choice, ManagementQuizAdapter.ManagementQuizViewHolder>(ManagementQuizDiffUtil.diffUtil) {
    private var currentIndex = ManagementQuizUtils.answer.size.minus(1)

    @SuppressLint("NotifyDataSetChanged")
    inner class ManagementQuizViewHolder(
        private val bind: ManagementQuizQuestionItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(choice: Choice) = bind.apply {
            question = choice
            view.apply {
                if (currentIndex == adapterPosition){
                    setBackgroundResource((R.drawable.shape_rectangle_stroke_radius_10px))
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
                ManagementQuizUtils.answer.clear()
                ManagementQuizUtils.answer.add(ManagementQuizUtils.questions[0].choices[adapterPosition].id)
                currentIndex = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagementQuizViewHolder {
        return ManagementQuizViewHolder(
            ManagementQuizQuestionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ManagementQuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}