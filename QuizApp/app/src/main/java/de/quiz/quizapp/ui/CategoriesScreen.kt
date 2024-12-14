package de.quiz.quizapp.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.quiz.quizapp.DBHelper
import de.quiz.quizapp.ui.theme.QuizAppTheme

@Composable
fun CategoriesScreen(
    context: Context,
    onCategoryButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        DBHelper.getCategories(context).forEach { category ->
            NextScreenButton(label = category.name, onClick = {onCategoryButtonClicked(category.id)}, modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(40.dp))
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
fun CategoriesScreenPreview() {
    QuizAppTheme {
        val context = LocalContext.current
        CategoriesScreen(context, onCategoryButtonClicked = {})
    }
}