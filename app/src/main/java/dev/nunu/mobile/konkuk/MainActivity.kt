package dev.nunu.mobile.konkuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.nunu.mobile.konkuk.navigation.NavigationRootScreen
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme
import dev.nunu.mobile.konkuk.words.WordsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileProgrammingTheme {
                // A surface container using the 'background' color from the theme
                NavigationRootScreen()
            }
        }
    }
}
