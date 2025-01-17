package de.quiz.quizapp

import android.content.Context
import de.quiz.quizapp.models.Category
import kotlin.math.roundToInt

class StatisticsHelper {

    companion object {

        fun add(context: Context, categoryId: Int, rightAnswered: Boolean) {
            val sharedPref = context.getSharedPreferences("STATISTICS", Context.MODE_PRIVATE)

            val totalForCategory = sharedPref.getInt("$categoryId total", 0)
            var rightAnsweredForCategory = sharedPref.getInt("$categoryId right", 0)

            if (rightAnswered) {
                rightAnsweredForCategory += 1
            }

            val editor = sharedPref.edit()
            editor.putInt("$categoryId total", totalForCategory + 1)
            editor.putInt("$categoryId right", rightAnsweredForCategory)
            editor.apply()
        }

        fun get(context: Context): HashMap<Int, Float> {
            val statisticsHashMap = HashMap<Int, Float>()

            DBHelper.getCategories(context).forEach { category ->
                val sharedPref = context.getSharedPreferences("STATISTICS", Context.MODE_PRIVATE)

                val totalForCategory = sharedPref.getInt("${category.id} total", 0)
                val rightAnsweredForCategory = sharedPref.getInt("${category.id} right", 0)

                if (totalForCategory == 0) {
                    statisticsHashMap[category.id] = 0f
                } else {
                    statisticsHashMap[category.id] =
                        rightAnsweredForCategory.toFloat() / totalForCategory.toFloat()
                }
            }

            return statisticsHashMap
        }

        fun reset(context: Context) {

            DBHelper.getCategories(context).forEach { category ->
                val sharedPref = context.getSharedPreferences("STATISTICS", Context.MODE_PRIVATE)

                val editor = sharedPref.edit()
                editor.putInt("${category.id} total", 0)
                editor.putInt("${category.id} right", 0)
                editor.apply()
            }

        }

    }
}