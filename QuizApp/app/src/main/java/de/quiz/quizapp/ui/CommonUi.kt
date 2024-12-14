package de.quiz.quizapp.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.quiz.quizapp.R

@Composable
fun NextScreenButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = 250.dp)
            .heightIn(min = 60.dp)
    ) {
        Text(
            label,
            fontSize = dimensionResource(R.dimen.fontsize_big).value.sp
        )
    }
}