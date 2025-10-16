package com.umain.basicandroidintegration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umain.basicandroidintegration.detail.DetailScreen
import com.umain.basicandroidintegration.presentation.MainScreen
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
object MainScreen

@Serializable
object DetailScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreen,
    ) {
        composable<MainScreen> {
            MainScreen(
                onNavigate = {
                    navController.navigate(route = DetailScreen)
                }
            )
        }
        composable<DetailScreen> {
            DetailScreen(
            )
        }
    }
}
