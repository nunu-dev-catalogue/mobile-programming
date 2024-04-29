package dev.nunu.mobile.konkuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dev.nunu.mobile.konkuk.navigation.login.LoginNavGraph
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileProgrammingTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                LoginNavGraph(navController = navController)
            }
        }
    }
}
