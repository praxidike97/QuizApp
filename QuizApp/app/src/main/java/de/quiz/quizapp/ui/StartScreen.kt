package de.quiz.quizapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.quiz.quizapp.R
import de.quiz.quizapp.ui.theme.QuizAppTheme

@Composable
fun StartScreen(
    onInfiniteModeButtonClicked: () -> Unit,
    onCategoriesButtonClicked: () -> Unit,
    onAboutButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface {
        Column(
            modifier = modifier,
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "Just Quiz",
                fontSize = dimensionResource(R.dimen.fontsize_huge).value.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        NextScreenButton(label = "Infinite mode",
            onClick = { onInfiniteModeButtonClicked() },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(40.dp))

        NextScreenButton(label = "Categories",
            onClick = { onCategoriesButtonClicked() },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(40.dp))
        
        NextScreenButton(label = "About",
            onClick = { onAboutButtonClicked() },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally))
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun StartScreenPreview() {
    QuizAppTheme {
        StartScreen(
            onInfiniteModeButtonClicked = {},
            onCategoriesButtonClicked = {},
            onAboutButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}