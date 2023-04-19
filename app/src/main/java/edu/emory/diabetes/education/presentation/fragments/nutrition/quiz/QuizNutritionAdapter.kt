package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.databinding.NutritionQuizQuestionItemBinding
import edu.emory.diabetes.education.domain.model.AnswerData
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.domain.model.QuizUserResponse
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapterEvent
import javax.inject.Inject

class QuizNutritionAdapter @Inject constructor(private val viewModel:NutritionQuizQuestionViewModel,
    val onEvent: (QuizAdapterEvent) -> Unit
) : RecyclerView.Adapter<QuizNutritionAdapter.ViewHolder>() {
    private var quizId: Int? = null
    private val selectedIndexes: MutableList<Int> = ArrayList()
    val asyncListDiffer = AsyncListDiffer(this, QuizNutritionDiffUtil.diffUtil)
    var maxAnswerSize = 0
    var maxChoicesSize = 0
    private lateinit var answers: List<String>
    private var wrongChoiceIndexes: MutableList<AnswerData> = ArrayList()
    private var onSubmitStateClicked = false
    var listener: Utils.OnSubmitResultStateListener? = null
    var trialCount:Int = 0
    var pos = ""

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: NutritionQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {


        fun bind(choice: Choice) = bind.apply {
            when (!onSubmitStateClicked) {
                true -> {
                    val results =viewModel.userResponse.value
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
                            results?.let {res->
                                val iterator = res.answerData.iterator()
                                while (iterator.hasNext()){
                                    val element = iterator.next()
                                    if (element.choiceIndex == it){
                                        if (choice.id == element.choice) {
                                            bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_900))
                                            setBackgroundResource(R.drawable.shape_rectangle_wrong_choice_stroke_radius_10px)
                                            bind.checkBox.isChecked = true
                                            if(pos.isNotEmpty()){
                                                if (element.choiceIndex == Integer.parseInt(pos)){
                                                    bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                                                    setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                                                    bind.checkBox.isChecked = true
                                                    iterator.remove()
                                                }
                                            }
                                        }
                                    }
                                }
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
                    val results =viewModel.userResponse.value
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
                        results?.let { quizUserResponse ->
                            quizUserResponse.answerData.forEach {
                                if (choice.id == it.choice) {
                                    bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_900))
                                    setBackgroundResource(R.drawable.shape_rectangle_wrong_choice_stroke_radius_10px)
                                    bind.checkBox.isChecked = true
                                    //quizUserResponse.answerData.remove(it)
                                }
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
                QuizNutritionUtil.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
                            wrongChoiceIndexes.clear()
                            selectedIndexes.clear()
                        }
                        remove(adapterPosition)
                        pos =""
                    } else if (size > maxChoicesSize.minus(1)) {
                        if (maxChoicesSize > 1) {
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
                            if(adapterPosition>=0){
                                add(adapterPosition)
                                pos ="$adapterPosition"
                            }
                        }
                    } else{
                        if (adapterPosition>=0) add(adapterPosition)
                    }
                    pos ="$adapterPosition"
                }.onEach {
                    QuizNutritionUtil.answer.add(QuizNutritionUtil.questions[0].choices[it].id)
                }
                notifyDataSetChanged()
            }
            bind.checkBox.setOnClickListener {
                onEvent.invoke(QuizAdapterEvent.ItemClicked)
                if (onSubmitStateClicked) onSubmitStateClicked = false
                QuizNutritionUtil.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
                            wrongChoiceIndexes.clear()
                            selectedIndexes.clear()
                        }
                        remove(adapterPosition)
                        pos =""
                    } else if (size > maxChoicesSize.minus(1)) {
                        if (maxChoicesSize > 1) {
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
                            if(adapterPosition>=0){
                                add(adapterPosition)
                                pos ="$adapterPosition"
                            }
                        }
                    } else{
                        if (adapterPosition>=0) add(adapterPosition)
                    }
                    pos ="$adapterPosition"
                }.onEach {
                    QuizNutritionUtil.answer.add(QuizNutritionUtil.questions[0].choices[it].id)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NutritionQuizQuestionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount() = asyncListDiffer.currentList.size


    fun setAnswers(question: Question, quizId: Int, submittedAns: List<String>) {
        trialCount ++
        if (!onSubmitStateClicked) onSubmitStateClicked = true
        this@QuizNutritionAdapter.quizId = quizId
        answers = submittedAns

        if (Utils.hasAllAnswers(submittedAns, question.answers)) {
            if (onSubmitStateClicked) onSubmitStateClicked = false
            listener?.onSubmitResultState(
                Utils.RESULTS_ON_SUBMIT.HAS_ALL_CORRECT,
                answers.sorted().joinToString(", "),
                false
            )
        } else {
            if (Utils.hasSomeAnswers(submittedAns, question.answers, 5)) {
                val wrongChoice = answers.subtract(question.answers.toSet())
                val wrongChoiceIndexes = mutableListOf<AnswerData>()
                if (wrongChoice.isEmpty()) {
                    listener?.onSubmitResultState(
                        Utils.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT,
                        "Please choose all answers that apply.",
                        hasSomeAllCorrect = true
                    )
                } else {
                    listener?.onSubmitResultState(
                        Utils.RESULTS_ON_SUBMIT.HAS_SOME_CORRECT,
                        "${answers.sorted().joinToString(separator = ", ")} is not right. Please choose again.",
                        hasSomeAllCorrect = false
                    )
                }

                for (i in wrongChoice.indices) {
                    var count = 0
                    question.choices.forEach { choice ->
                        if (choice.id == wrongChoice.elementAt(i)) wrongChoiceIndexes.add(AnswerData(wrongChoice.elementAt(i), count))
                        count++
                    }
                }
                if (trialCount>0){
                    if (this.wrongChoiceIndexes.size!=0) this.wrongChoiceIndexes = wrongChoiceIndexes
                }else{
                    this.wrongChoiceIndexes = wrongChoiceIndexes
                }
                val userResponse= QuizUserResponse(selectedIndexes,wrongChoiceIndexes)
                viewModel.setUserResponse(userResponse)
            } else {
                val wrongChoice = answers.subtract(question.answers.toSet())
                val wrongChoiceIndexes = mutableListOf<AnswerData>()
                listener?.onSubmitResultState(
                    Utils.RESULTS_ON_SUBMIT.HAS_NONE_CORRECT,
                    "${answers.sorted().joinToString(separator = ", ")} is not right. Please choose again.",
                    false
                )

                for (i in wrongChoice.indices) {
                    var count = 0
                    question.choices.forEach { choice ->
                        if (choice.id == wrongChoice.elementAt(i)) wrongChoiceIndexes.add(AnswerData(wrongChoice.elementAt(i), count))
                        count++
                    }
                }
                if (trialCount>0){
                    if (this.wrongChoiceIndexes.size!=0) this.wrongChoiceIndexes = wrongChoiceIndexes
                }else{
                    this.wrongChoiceIndexes = wrongChoiceIndexes
                }

                val userResponse= QuizUserResponse(selectedIndexes,wrongChoiceIndexes)
                viewModel.setUserResponse(userResponse)
            }
        }
        notifyDataSetChanged()
    }
}