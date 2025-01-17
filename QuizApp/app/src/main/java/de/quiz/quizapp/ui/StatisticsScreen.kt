package de.quiz.quizapp.ui

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.quiz.quizapp.DBHelper
import de.quiz.quizapp.R
import de.quiz.quizapp.StatisticsHelper
import de.quiz.quizapp.ui.theme.QuizAppTheme
import kotlin.math.roundToInt

@Composable
fun StatisticsScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    var openDialog = remember { mutableStateOf(false) }

    Surface {
        Column(
            modifier = modifier.padding(horizontal = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val statisticsMap = StatisticsHelper.get(context)

            DBHelper.getCategories(context).forEach { category ->

                Spacer(modifier = Modifier.height(60.dp))

                Column(modifier = Modifier) {
                    Row(modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = category.name)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "${(statisticsMap.getOrDefault(category.id, 0f) * 100.0).roundToInt()}%")
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { statisticsMap.getOrDefault(category.id, 0f) },
                        color = Color(0xFFBB86FC),
                        modifier = Modifier
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxWidth()
                    )
                }

            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {
                    openDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF746C)),
                modifier = modifier
                    .widthIn(min = 250.dp)
                    .heightIn(min = 60.dp)
            ) {
                Text(
                    "Reset",
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.fontsize_big).value.sp
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            if (openDialog.value) {
                AlertDialog(
                    title = {
                        Text(text = "Warning")
                    },
                    text = {
                        Text(text = "Are you sure that you really want to reset all statistics?")
                    },
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                StatisticsHelper.reset(context)
                                openDialog.value = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }

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
fun StatisticsScreenPreview() {
    QuizAppTheme {
        val context = LocalContext.current
        StatisticsScreen(context)
    }
}