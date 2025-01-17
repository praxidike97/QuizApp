package de.quiz.quizapp.ui

import android.content.Context
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.quiz.quizapp.DBHelper
import de.quiz.quizapp.StatisticsHelper
import de.quiz.quizapp.models.Question
import de.quiz.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuestionScreen(context: Context, categoryId: Int) {
    val scope = rememberCoroutineScope()
    var selectedRed by remember { mutableStateOf(0) }
    var selectedGreen by remember { mutableStateOf(0) }
    var question by remember { mutableStateOf(if (categoryId == -1) DBHelper.getRandomQuestion(context) else DBHelper.getQuestionByCategory(context, categoryId)) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Column(
                    modifier = Modifier
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
            AnswerCard(scope,
                context,
                1,
                selectedGreen,
                selectedRed,
                onSelectedGreenChange = { selectedGreen = it },
                onSelectedRedChange = { selectedRed = it },
                question,
                onQuestionChange = { question = it },
                categoryId
            )
            Spacer(modifier = Modifier.height(20.dp))
            AnswerCard(scope,
                context,
                2,
                selectedGreen,
                selectedRed,
                onSelectedGreenChange = { selectedGreen = it },
                onSelectedRedChange = { selectedRed = it },
                question,
                onQuestionChange = { question = it },
                categoryId
            )

            if (!question.hasTwoAnswers()) {
                Spacer(modifier = Modifier.height(20.dp))
                AnswerCard(scope,
                    context,
                    3,
                    selectedGreen,
                    selectedRed,
                    onSelectedGreenChange = { selectedGreen = it },
                    onSelectedRedChange = { selectedRed = it },
                    question,
                    onQuestionChange = { question = it },
                    categoryId
                )
                Spacer(modifier = Modifier.height(20.dp))
                AnswerCard(scope,
                    context,
                    4,
                    selectedGreen,
                    selectedRed,
                    onSelectedGreenChange = { selectedGreen = it },
                    onSelectedRedChange = { selectedRed = it },
                    question,
                    onQuestionChange = { question = it },
                    categoryId
                )
            }
        }
    }
}

@Composable
fun AnswerCard(scope: CoroutineScope, context: Context, buttonNumber: Int, selectedGreen: Int, selectedRed: Int,
               onSelectedGreenChange: (Int) -> Unit, onSelectedRedChange: (Int) -> Unit,
               question: Question, onQuestionChange: (Question) -> Unit, categoryId: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            if (selectedGreen == 0 && selectedRed == 0) {
                if (buttonNumber == question.solution) {
                    onSelectedGreenChange(buttonNumber)
                    StatisticsHelper.add(context, question.category, true)
                } else {
                    onSelectedGreenChange(question.solution)
                    onSelectedRedChange(buttonNumber)
                    StatisticsHelper.add(context, question.category, false)
                }

                scope.launch {
                    delay(1200)
                    onSelectedGreenChange(0)
                    onSelectedRedChange(0)

                    if (categoryId == -1) {
                        onQuestionChange(DBHelper.getRandomQuestion(context))
                    }else{
                        onQuestionChange(DBHelper.getQuestionByCategory(context, categoryId))
                    }
                }
            }
        },
        colors = cardColors( containerColor = when {
            buttonNumber == selectedGreen -> Color(0xFF56AE57)
            buttonNumber == selectedRed -> Color(0xFFFF746C)
            else -> MaterialTheme.colorScheme.background
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
            val context = LocalContext.current
            QuestionScreen(context, 1)
        }
    }
}