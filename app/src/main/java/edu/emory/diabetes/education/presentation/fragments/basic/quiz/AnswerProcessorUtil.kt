package edu.emory.diabetes.education.presentation.fragments.basic.quiz

object AnswerProcessorUtil {
    fun hasAllAnswers(submittedAnswers: List<String>,answers: List<String>): Boolean {
        if (submittedAnswers.size>answers.size){
            return false
        }else{
            for (element in answers) {
                if (!submittedAnswers.contains(element)) {
                    return false
                }
            }
            return true
        }
    }
    fun hasSomeAnswers(submittedAnswers: List<String>,answers: List<String>,maximumAnswers:Int):Boolean{
        val found=answers.intersect(submittedAnswers.toSet())
        return found.isNotEmpty()
    }
    enum class RESULTS_ON_SUBMIT {
        HAS_ALL_CORRECT,
        HAS_SOME_CORRECT,
        HAS_NONE_CORRECT,
    }
    //Interfaces
    interface OnSubmitResultStateListener{
        fun onSubmitResultState(resultInfo:RESULTS_ON_SUBMIT,answerChoice:String,hasSomeAllCorrect:Boolean)
    }

}