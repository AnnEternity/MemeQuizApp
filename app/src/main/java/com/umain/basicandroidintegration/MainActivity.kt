package com.umain.basicandroidintegration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umain.basicandroidintegration.main.MainQuizScreen
import com.umain.basicandroidintegration.main.QuizTheme
import com.umain.basicandroidintegration.quiz.QuizScreen
import com.umain.basicandroidintegration.ui.theme.BasicAndroidIntegrationTheme
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicAndroidIntegrationTheme {
                MainNavigation()
            }
        }
    }
}

@Serializable
object MainQuizScreen

@Serializable
data class DetailScreen(val theme: QuizTheme)

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainQuizScreen,
    ) {
        composable<MainQuizScreen> {
            MainQuizScreen(
                onNavigate = {
                    navController.navigate(route = DetailScreen(it))
                }
            )
        }
        composable<DetailScreen> {
            QuizScreen(
                navigateToStart = {navController.popBackStack()}
            )
        }
    }
}
