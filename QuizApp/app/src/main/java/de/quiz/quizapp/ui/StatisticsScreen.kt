package de.quiz.quizapp.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import de.quiz.quizapp.ui.theme.QuizAppTheme

@Composable
fun StatisticsScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            modifier = modifier.padding(horizontal = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Statistics",
                fontSize = dimensionResource(R.dimen.fontsize_huge).value.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            DBHelper.getCategories(context).forEach { category ->

                Spacer(modifier = Modifier.height(60.dp))

                Column(modifier = Modifier) {
                    Row(modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = category.name)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "80%")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { 0.1f },
                        color = Color.Red,
                        modifier = Modifier
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxWidth()
                    )
                }

            }

            Spacer(modifier = Modifier.height(60.dp))

            /*

            Spacer(modifier = Modifier.height(60.dp))

            Column (modifier = Modifier) {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Categoryyyyyy 1")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "80%")
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(progress = { 0.6f },
                    color = Color.Green,
                    modifier = Modifier
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth())
            }*/
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