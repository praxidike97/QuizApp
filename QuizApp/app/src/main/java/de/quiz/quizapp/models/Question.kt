package de.quiz.quizapp.models

class Question(
    val question: String,
    val answer01: String,
    val answer02: String,
    val answer03: String,
    val answer04: String,
    val solution: Int,
    val category: Int
) {
    fun hasTwoAnswers(): Boolean {
        return answer03 == "" && answer04 == ""
    }

    fun getAnswer(number: Int): String {
        when (number) {
            1 -> return answer01
            2 -> return answer02
            3 -> return answer03
            4 -> return answer04
            else -> return ""
        }
    }
}