package de.quiz.quizapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.quiz.quizapp.models.Question
import de.quiz.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LOGTAG", assetFilePath(this, "questions.sqlite"))
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                ) {
                    QuestionCard()
                }
            }
        }
    }
}

fun assetFilePath(context: Context, asset: String): String {
    val file = File(context.filesDir, asset)

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

@Composable
fun QuestionCard() {
    val scope = rememberCoroutineScope()
    var selectedRed by remember { mutableStateOf(0) }
    var selectedGreen by remember { mutableStateOf(0) }
    var question by remember { mutableStateOf(DBHelper.getRandomQuestion()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)){
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        AnswerCard(scope, 1, selectedGreen, selectedRed, onSelectedGreenChange = {selectedGreen = it},
            onSelectedRedChange = {selectedRed = it}, question, onQuestionChange = {question = it})
        Spacer(modifier = Modifier.height(20.dp))
        AnswerCard(scope, 2, selectedGreen, selectedRed, onSelectedGreenChange = {selectedGreen = it},
            onSelectedRedChange = {selectedRed = it}, question, onQuestionChange = {question = it})

        if (!question.hasTwoAnswers()) {
        Spacer(modifier = Modifier.height(20.dp))
            AnswerCard(scope, 3, selectedGreen, selectedRed, onSelectedGreenChange = {selectedGreen = it},
                onSelectedRedChange = {selectedRed = it}, question, onQuestionChange = {question = it})
        Spacer(modifier = Modifier.height(20.dp))
            AnswerCard(scope, 4, selectedGreen, selectedRed, onSelectedGreenChange = {selectedGreen = it},
                onSelectedRedChange = {selectedRed = it}, question, onQuestionChange = {question = it})
            }
    }
}

@Composable
fun AnswerCard(scope: CoroutineScope, buttonNumber: Int, selectedGreen: Int, selectedRed: Int,
               onSelectedGreenChange: (Int) -> Unit, onSelectedRedChange: (Int) -> Unit,
               question: Question, onQuestionChange: (Question) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            if (selectedGreen == 0 && selectedRed == 0) {

                Log.d("LOGTAG", "buttonNumber: $buttonNumber  question.solution: ${question.solution}")
                if (buttonNumber == question.solution) {
                    onSelectedGreenChange(buttonNumber)
                } else {
                    onSelectedGreenChange(question.solution)
                    onSelectedRedChange(buttonNumber)
                }

                scope.launch {
                    delay(1200)
                    onSelectedGreenChange(0)
                    onSelectedRedChange(0)
                    onQuestionChange(DBHelper.getRandomQuestion())
                }
            }
        },
        colors = cardColors( containerColor = when {
            buttonNumber == selectedGreen -> Color.Green
            buttonNumber == selectedRed -> Color.Red
            else -> MaterialTheme.colorScheme.surface
        }),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)){
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center) {
            Text(
                text = question.getAnswer(buttonNumber),
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    QuizAppTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
        ) {
            QuestionCard()
        }
    }
}