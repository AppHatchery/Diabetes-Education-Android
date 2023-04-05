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
import edu.emory.diabetes.education.domain.model.QuizUserResponse
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapter.ViewHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class QuizAdapter @Inject constructor(private val viewModel: QuizQuestionViewModel,
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
    var trialCount:Int = 0
    var pos = ""


    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: FragmentQuizQuestionItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(choice: Choice) = bind.apply {
            when (!onSubmitStateClicked) {

                true -> {
                    val results =viewModel.userResponse.value
                    //Log.e("Submit state is false","false sel $selectedIndexes res ${results?.answerData}")

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
                                                    Log.e("Adapter position selected ","Its $pos")

                                                    bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                                                    setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                                                    bind.checkBox.isChecked = true
                                                    iterator.remove()

                                                }
                                            }

                                            //Log.e("haha","haha ${choice.id} item ${itemData.choiceIndex} count $trialCount  pos $pos")
                                            // if (itemData.choiceIndex ==adapterPosition)

                                        }

                                    }
                                }
                                /*
                                res.answerData.forEach { itemData->
                                    if (itemData.choiceIndex == it){
                                        if (choice.id == itemData.choice) {
                                            bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_900))
                                            setBackgroundResource(R.drawable.shape_rectangle_wrong_choice_stroke_radius_10px)
                                            bind.checkBox.isChecked = true
                                           if(pos.isNotEmpty()){
                                               if (itemData.choiceIndex == Integer.parseInt(pos)){
                                                   Log.e("Adapter position selected ","Its $pos")

                                                   bind.checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_900))
                                                   setBackgroundResource(R.drawable.shape_rectangle_stroke_radius_10px)
                                                   bind.checkBox.isChecked = true
                                                   viewModel.userResponse.value?.let { ur->
                                                    ur.answerData.remove(itemData)
                                                   }

                                               }
                                           }

                                            //Log.e("haha","haha ${choice.id} item ${itemData.choiceIndex} count $trialCount  pos $pos")
                                           // if (itemData.choiceIndex ==adapterPosition)

                                        }

                                    }
                                }*/
                            }
                            //val quizUserResponse=QuizUserResponse(listOf(), listOf())
                            //viewModel.setUserResponse(quizUserResponse)

                        }
                        wrongChoiceIndexes.forEach {
                            if (selectedIndexes.contains(it.choiceIndex)) {
                                selectedIndexes.remove(it.choiceIndex)
                                wrongChoiceIndexes.remove(it)
                            }
                        }
                        if(wrongChoiceIndexes.size>0){
                            Log.e("how big is wr" ,"${wrongChoiceIndexes.size} ")
                        }
                    }
                    executePendingBindings()
                }
                false -> {
                    val results =viewModel.userResponse.value
                    //Log.e("Submit state is false","true ${viewModel.userResponse.value?.answerData?.size}")


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
                QuizUtils.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
                            wrongChoiceIndexes.clear()
                            selectedIndexes.clear()
                        }
                        remove(adapterPosition)
                        pos =""
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
                            pos ="$adapterPosition"
                        }
                    } else
                        add(adapterPosition)
                        pos ="$adapterPosition"
                    if (wrongChoiceIndexes.size > 0) {
                        //selectedIndexes.clear()
                        //wrongChoiceIndexes.clear()
                        TODO("if you encounter an error un comment these")

                    }
                }.onEach {
                    QuizUtils.answer.add(QuizUtils.questions[0].choices[it].id)
                }
                notifyDataSetChanged()
            }
            bind.checkBox.setOnClickListener {
                //viewModel.setItemPosition("$adapterPosition")
                if (onSubmitStateClicked) onSubmitStateClicked = false
                QuizUtils.answer.clear()
                selectedIndexes.apply {
                    if (contains(adapterPosition)) {
                        if (wrongChoiceIndexes.size > 0) {
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
        trialCount ++
        Log.e("Quizcount","count $trialCount")
        Log.e("SELECTED indexes" ,"$selectedIndexes  wr ${wrongChoiceIndexes.size}")
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
                if (trialCount>0){
                    if (this.wrongChoiceIndexes.size!=0){
                        this.wrongChoiceIndexes = wrongChoiceIndexes
                    }
                }else{
                    this.wrongChoiceIndexes = wrongChoiceIndexes
                }
                val userResponse= QuizUserResponse(selectedIndexes,wrongChoiceIndexes)
                viewModel.setUserResponse(userResponse)
                Log.e("wrong indexes---" ,"${wrongChoiceIndexes.size}")

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
                Log.e("wrong indexes---" ,"${wrongChoiceIndexes.size}")
                if (trialCount>0){
                    if (this.wrongChoiceIndexes.size!=0){
                        this.wrongChoiceIndexes = wrongChoiceIndexes
                    }
                }else{
                    this.wrongChoiceIndexes = wrongChoiceIndexes
                }
                val userResponse= QuizUserResponse(selectedIndexes,wrongChoiceIndexes)
                viewModel.setUserResponse(userResponse)

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