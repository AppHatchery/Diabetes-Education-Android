package edu.emory.diabetes.education.presentation.fragments.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.presentation.fragments.management.quiz.ManagementQuizUtils
import edu.emory.diabetes.education.presentation.fragments.quiz.QuizAdapter.ViewHolder
import edu.emory.diabetes.education.presentation.setBackgroundColorResource

class QuizAdapter(
    val onEven: () -> Unit
) : ListAdapter<Choice, ViewHolder>(QuizDiffUtil.diffUtil) {

    private var currentIndex = QuizUtils.answer.size.minus(1)
    private var selectedIndex = mutableListOf<Int>()
    private var isSelected:Boolean = false
    private val flag= "check all that apply"

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: FragmentQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        private val question = QuizUtils.questions[0]

        fun bind(choice: Choice) = bind.apply {
            question = choice
            bind.view.apply {
                if (currentIndex == adapterPosition){
                    setBackgroundResource((R.drawable.shape_rectangle_stroke_radius_10px))
                    background.setTint(context.getColor(R.color.green_100))
                }else{
                    setBackgroundResource(R.drawable.shape_rectangle_radius_10px)
                    background.setTint(Color.WHITE)
                }
            }
            executePendingBindings()
        }


        private fun didTap(){
            bind.view.apply {
                onEven.invoke()
                if (selectedIndex.contains(adapterPosition)) {
                    selectedIndex.remove(adapterPosition)
                    setBackgroundResource(R.drawable.shape_rectangle_radius_10px)
                    background.setTint(Color.WHITE)
                } else {
                    selectedIndex.add(adapterPosition)
                    QuizUtils.answer.clear()
                    QuizUtils.answer.add(QuizUtils.questions[0].choices[adapterPosition].id)
                    setBackgroundResource((R.drawable.shape_rectangle_stroke_radius_10px))
                    background.setTint(context.getColor(R.color.green_100))

                }
                notifyDataSetChanged()
            }

        }

        init {

//                bind.root.setOnLongClickListener(object: View.OnLongClickListener {
//                    override fun onLongClick(v: View?): Boolean {
//                        isSelected = true
//                        didTap()
//                        if (selectedIndex.size == 0) isSelected = false
//                        return  true
//                    }
//                })
//                bind.root.setOnClickListener{
//                    if (isSelected){
//                        didTap()
//                        if (selectedIndex.size == 0) isSelected = false
//                    }
//                }
                bind.root.setOnClickListener {
                    Log.e("TAG", "Desc: "+question.description )
                    onEven.invoke()
                    QuizUtils.answer.clear()
                    QuizUtils.answer.add(ManagementQuizUtils.questions[0].choices[adapterPosition].id)
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