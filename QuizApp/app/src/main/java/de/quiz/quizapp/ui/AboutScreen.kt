package de.quiz.quizapp.ui

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.quiz.quizapp.DBHelper
import de.quiz.quizapp.R
import de.quiz.quizapp.ui.theme.QuizAppTheme

@Composable
fun AboutScreen() {

    Surface {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            val annotatedString = buildAnnotatedString {
                append("All the questions in this quiz app are from ")

                withLink(LinkAnnotation.Url(url = "https://github.com/uberspot/OpenTriviaQA")) {
                    withStyle(style = SpanStyle(color = Color(0xFF0000EE))) {
                        append("https://github.com/uberspot/OpenTriviaQA.")
                    }
                }

                append("\n")
                append("\n")
                append("They are licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit ")

                withLink(LinkAnnotation.Url(url = "http://creativecommons.org/licenses/by-sa/4.0/")) {
                    withStyle(style = SpanStyle(color = Color(0xFF0000EE))) {
                        append("http://creativecommons.org/licenses/by-sa/4.0/")
                    }
                }


            }

            Text(
                text = annotatedString,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Made with ❤\uFE0F in Germany",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
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
fun AboutScreenPreview() {
    QuizAppTheme {
        val context = LocalContext.current
        AboutScreen()
    }
}