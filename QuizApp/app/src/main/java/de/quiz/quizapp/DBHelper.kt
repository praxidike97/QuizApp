package de.quiz.quizapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.quiz.quizapp.models.Question
import kotlin.random.Random

class DBHelper {

    companion object {
        private val db = SQLiteDatabase.openDatabase("/data/user/0/de.quiz.quizapp/files/questions.sqlite", null, SQLiteDatabase.OPEN_READONLY)

        fun getRandomQuestion(): Question {
            var cursor = db.rawQuery("SELECT COUNT(*) FROM questions", null)
            cursor.moveToFirst()
            var numberQuestions = cursor.getInt(0)
            cursor.close()

            val randomQuestionId = Random.nextInt(1, numberQuestions)

            cursor = db.rawQuery("SELECT * FROM questions WHERE id=$randomQuestionId", null)
            cursor.moveToFirst()
            val question = Question(cursor.getString(1), cursor.getString(2),
                cursor.getString(3),  cursor.getString(4),
                cursor.getString(5), cursor.getInt(6),
                cursor.getInt(7))
            cursor.close()

            return question
        }
    }
}