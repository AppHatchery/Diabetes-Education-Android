package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.ManagementQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapterEvent

class ManagementQuizQuestionAdapter(
    val onEvent: (QuizAdapterEvent) -> Unit
) : RecyclerView.Adapter<ManagementQuizQuestionAdapter.ViewHolder>() {

    private val selectedIndexes: MutableList<Int> = ArrayList()
    val asyncListDiffer = AsyncListDiffer(this, ManagementQuizDiffUtil.diffUtil)
    var maxAnswerSize = 0

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: ManagementQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {


        fun bind(choice: Choice) = bind.apply {
            question = choice
            bind.view.apply {
                asyncListDiffer.currentList.forEach { _ -> setBackgroundResource(R.drawable.shape_rectangle_white_radius_90px) }
                selectedIndexes.forEach {
                    if (it == adapterPosition) setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                }
            }
            executePendingBindings()
        }


        init {
            bind.root.setOnClickListener {
                ManagementQuizUtils.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) remove(adapterPosition)
                    else if (size > maxAnswerSize.minus(1)) {
                        if (maxAnswerSize > 1) onEvent.invoke(QuizAdapterEvent.MaximumLimit) else {
                            removeFirst()
                            add(adapterPosition)
                        }
                    } else
                        add(adapterPosition)
                }.onEach {
                    ManagementQuizUtils.answer.add(ManagementQuizUtils.questions[0].choices[it].id)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ManagementQuizQuestionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount() = asyncListDiffer.currentList.size
}