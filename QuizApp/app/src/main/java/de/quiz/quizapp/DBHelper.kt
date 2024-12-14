package de.quiz.quizapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import de.quiz.quizapp.models.Category
import de.quiz.quizapp.models.Question
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

class DBHelper {

    companion object {

        private fun assetFilePath(context: Context, asset: String): String {
            val file = File(context. filesDir, asset)

            try {
                val inpStream: InputStream = context.assets.open(asset)
                try {
                    val outStream = FileOutputStream(file, false)
                    val buffer = ByteArray(4 * 1024)
                    var read: Int

                    while (true) {
                        read = inpStream.read(buffer)
                        if (read == -1) {
                            break
                        }
                        outStream.write(buffer, 0, read)
                    }
                    outStream.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun getRandomQuestion(context : Context): Question {
            val categories = getCategories(context)
            return getQuestionByCategory(context, categories.random().id)
        }

        fun getCategories(context: Context): ArrayList<Category> {
            val db = SQLiteDatabase.openDatabase(assetFilePath(context, "questions.sqlite"), null, SQLiteDatabase.OPEN_READONLY)

            val categoriesList = ArrayList<Category>()
            val cursor = db.rawQuery("SELECT * FROM categories", null)
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {
                categoriesList.add(Category(cursor.getString(0), cursor.getInt(1)))
                cursor.moveToNext()
            }

            cursor.close()
            db.close()
            return categoriesList
        }

        fun getQuestionByCategory(context: Context, categoryId: Int): Question {
            //            //private val db = SQLiteDatabase.openDatabase("/data/user/0/de.quiz.quizapp/files/questions.sqlite", null, SQLiteDatabase.OPEN_READONLY)
            val db = SQLiteDatabase.openDatabase(assetFilePath(context, "questions.sqlite"), null, SQLiteDatabase.OPEN_READONLY)

            /*var cursor = db.rawQuery("SELECT COUNT(*) FROM questions", null)
            cursor.moveToFirst()
            val numberQuestions = cursor.getInt(0)
            cursor.close()
            val randomQuestionId = Random.nextInt(1, numberQuestions)*/

            val cursor = db.rawQuery("SELECT * FROM questions WHERE category=$categoryId ORDER BY RANDOM() LIMIT 1", null)
            cursor.moveToFirst()
            val question = Question(cursor.getString(1), cursor.getString(2),
                cursor.getString(3),  cursor.getString(4),
                cursor.getString(5), cursor.getInt(6),
                cursor.getInt(7))
            cursor.close()

            db.close()
            return question
        }
    }
}