package de.quiz.quizapp

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.quiz.quizapp.ui.AboutScreen
import de.quiz.quizapp.ui.CategoriesScreen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import de.quiz.quizapp.ui.StartScreen
import de.quiz.quizapp.ui.QuestionScreen
import de.quiz.quizapp.ui.theme.QuizAppTheme

enum class QuizAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Question(title = R.string.question_screen),
    Categories(title = R.string.categories_screen),
    About(title = R.string.about_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun QuizApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = QuizAppScreen.valueOf(
        backStackEntry?.destination?.route ?: QuizAppScreen.Start.name
    )

    val context = LocalContext.current

    Scaffold(
        topBar = {
            QuizAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = QuizAppScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = QuizAppScreen.Start.name) {
                StartScreen(
                    onInfiniteModeButtonClicked = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("categoryId", -1)
                        navController.navigate(QuizAppScreen.Question.name)
                    },
                    onCategoriesButtonClicked = {
                        navController.navigate(QuizAppScreen.Categories.name)
                    },
                    onAboutButtonClicked = {
                        navController.navigate(QuizAppScreen.About.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = QuizAppScreen.Question.name) {
                var categoryId = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("categoryId")
                if (categoryId == null){
                    categoryId = -1
                }
                QuestionScreen(context, categoryId)
            }

            composable(route = QuizAppScreen.Categories.name) {
                CategoriesScreen(context,
                    onCategoryButtonClicked = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("categoryId", it)
                        navController.navigate(QuizAppScreen.Question.name)
                })
            }

            composable(route = QuizAppScreen.About.name) {
                AboutScreen()
            }

            /*
            composable(route = QuizAppScreen.Question.name) {
                val context = LocalContext.current
                QuestionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(QuizAppScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = QuizAppScreen.Pickup.name) {
                CategoriesScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(QuizAppScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = QuizAppScreen.Summary.name) {
                val context = LocalContext.current
                AboutScreen(
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
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
fun QuizAppPreview() {
    QuizAppTheme {
        QuizApp()
    }
}