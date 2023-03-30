package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.Answer
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapter.ViewHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class QuizAdapter(
    val onEvent: (QuizAdapterEvent) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var quizId: Int? = null
    private val selectedIndexes: MutableList<Int> = ArrayList()
    val asyncListDiffer = AsyncListDiffer(this, QuizDiffUtil.diffUtil)
    var maxAnswerSize = 0
    private lateinit var answers: List<String>
    private var wrongChoiceIndexes: MutableList<AnswerData> = ArrayList()
    private var onSubmitStateClicked = false
    var listener: AnswerProcessorUtil.OnSubmitResultStateListener? = null

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: FragmentQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(choice: Choice) = bind.apply {
            when (!onSubmitStateClicked) {
                true -> {
                    question = choice
                    bind.constraintLayoutItem.apply {
                        asyncListDiffer.currentList.forEach { _ ->
                            bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                            setBackgroundResource(R.drawable.shape_rectangle_white_radius_90px)
                            bind.checkBox.isChecked = false
                        }
                        selectedIndexes.forEach {
                            if (it == adapterPosition) {
                                bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                                setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                                bind.checkBox.isChecked = true
                            }
                        }
                        wrongChoiceIndexes.forEach {
                            if (selectedIndexes.contains(it.choiceIndex)) {
                                selectedIndexes.remove(it.choiceIndex)
                                wrongChoiceIndexes.remove(it)
                            }
                        }
                    }
                    executePendingBindings()
                }
                false -> {
                    question = choice
                    bind.constraintLayoutItem.apply {
                        asyncListDiffer.currentList.forEach { _ ->
                            bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                            setBackgroundResource(R.drawable.shape_rectangle_white_radius_90px)
                            bind.checkBox.isChecked = false
                        }
                        selectedIndexes.forEach {
                            if (it == adapterPosition) {
                                bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                                setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                                bind.checkBox.isChecked = true
                            }
                        }
                        wrongChoiceIndexes.forEach {
                            if (choice.id == it.choice) {
                                bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_900))
                                setBackgroundResource(R.drawable.shape_rectangle_wrong_choice_stroke_radius_10px)
                                bind.checkBox.isChecked = true
                            }
                        }
                    }
                    executePendingBindings()
                }
            }
        }

        init {
            bind.root.setOnClickListener {
                onEvent.invoke(QuizAdapterEvent.ItemClicked)
                if (onSubmitStateClicked) onSubmitStateClicked = false
                QuizUtils.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
                            wrongChoiceIndexes.clear()
                            selectedIndexes.clear()
                        }
                        remove(adapterPosition)
                    } else if (size > maxAnswerSize.minus(1)) {
                        if (maxAnswerSize > 1) {
                            onEvent.invoke(QuizAdapterEvent.MaximumLimit)
                            if (wrongChoiceIndexes.size > 0) {
                                selectedIndexes.clear()
                                wrongChoiceIndexes.clear()
                            }
                        } else {
                            removeFirst()
                            if (wrongChoiceIndexes.size > 0) {
                                selectedIndexes.clear()
                                wrongChoiceIndexes.clear()
                            }
                            add(adapterPosition)
                        }
                    } else
                        add(adapterPosition)
                    if (wrongChoiceIndexes.size > 0) {
                        selectedIndexes.clear()
                        wrongChoiceIndexes.clear()
                    }
                }.onEach {
                    QuizUtils.answer.add(QuizUtils.questions[0].choices[it].id)
                }
                notifyDataSetChanged()
            }
            bind.checkBox.setOnClickListener {
                if (onSubmitStateClicked) onSubmitStateClicked = false
                QuizUtils.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
                            wrongChoiceIndexes.clear()
                            selectedIndexes.clear()
                        }
                        remove(adapterPosition)
                    } else if (size > maxAnswerSize.minus(1)) {
                        if (maxAnswerSize > 1) {
                            onEvent.invoke(QuizAdapterEvent.MaximumLimit)
                            if (wrongChoiceIndexes.size > 0) {
                                selectedIndexes.clear()
                                wrongChoiceIndexes.clear()
                            }
                        } else {
                            removeFirst()
                            if (wrongChoiceIndexes.size > 0) {
                                selectedIndexes.clear()
                                wrongChoiceIndexes.clear()
                            }
                            add(adapterPosition)
                        }
                    } else
                        add(adapterPosition)
                    if (wrongChoiceIndexes.size > 0) {
                        selectedIndexes.clear()
                        wrongChoiceIndexes.clear()
                    }
                }.onEach {
                    QuizUtils.answer.add(QuizUtils.questions[0].choices[it].id)
                }
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

        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    fun setAnswers(question: Question, quizId: Int, submittedAns: List<String>) {
        if (!onSubmitStateClicked) onSubmitStateClicked = true
        this@QuizAdapter.quizId = quizId
        answers = submittedAns
        if (AnswerProcessorUtil.hasAllAnswers(submittedAns, question.answers)) {
            if (onSubmitStateClicked) onSubmitStateClicked = false
            listener?.onSubmitResultState(
                AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_ALL_CORRECT,
                "All correct",
                false
            )
        } else {
            if (AnswerProcessorUtil.hasSomeAnswers(submittedAns, question.answers, 5)) {
                val wrongChoice = answers.subtract(question.answers.toSet())
                val wrongChoiceIndexes = mutableListOf<AnswerData>()
                if (wrongChoice.isEmpty()) {
                    listener?.onSubmitResultState(
                        AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT,
                        "Please choose all answers that apply.",
                        hasSomeAllCorrect = true
                    )
                } else {
                    listener?.onSubmitResultState(
                        AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT,
                        "${answers.joinToString(separator = ", ")} is not right. Please choose again.",
                        hasSomeAllCorrect = false
                    )
                }

                for (i in wrongChoice.indices) {
                    var count = 0
                    question.choices.forEach { choice ->
                        if (choice.id == wrongChoice.elementAt(i)) {
                            wrongChoiceIndexes.add(AnswerData(wrongChoice.elementAt(i), count))
                        }
                        count++
                    }
                }
                this.wrongChoiceIndexes = wrongChoiceIndexes
            } else {
                val wrongChoice = answers.subtract(question.answers.toSet())
                val wrongChoiceIndexes = mutableListOf<AnswerData>()
                listener?.onSubmitResultState(
                    AnswerProcessorUtil.RESULTS_ON_SUBMIT.HAS_NONE_CORRECT,
                    "${answers.joinToString(separator = ", ")} is not right. Please choose again.",
                    false
                )
                for (i in wrongChoice.indices) {
                    var count = 0
                    question.choices.forEach { choice ->
                        if (choice.id == wrongChoice.elementAt(i)) {
                            wrongChoiceIndexes.add(AnswerData(wrongChoice.elementAt(i), count))
                        }
                        count++
                    }
                }
                this.wrongChoiceIndexes = wrongChoiceIndexes
            }
        }
        notifyDataSetChanged()
    }
    /*fun arrayToString(answers:List<String>){
    }*/
    data class AnswerData(val choice: String, val choiceIndex: Int)


    interface OnItemClickListener {
        fun onItemClick(p: Int)
    }
}