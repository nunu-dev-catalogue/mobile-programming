package dev.nunu.mobile.konkuk.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController

@Composable
fun recordBackStackEntry(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        navController.currentBackStackEntryFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect {
                Log.d("BackStackEntry", it.toString())
            }
    }
}
