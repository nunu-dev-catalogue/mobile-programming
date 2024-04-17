package dev.nunu.mobile.konkuk.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest

enum class Route(val route: String) {
    HOME("home"),
    DETAIL("detail"),
    ALPHA("alpha"),
    BETA("beta"),
    GAMMA("gamma")
}

@Composable
fun NavigationRootScreen() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = Route.HOME.route
        ) {
            composable(Route.HOME.route) {
                HomeScreen(navController)
            }
            composable(Route.DETAIL.route) {
                DetailScreen(navController)
            }
            composable(Route.ALPHA.route) {
                AlphaScreen(navController)
            }
            composable(Route.BETA.route) {
                BetaScreen(navController)
            }
            composable(Route.GAMMA.route) {
                GammaScreen(navController)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        navController.currentBackStack
        navController.currentBackStackEntryFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                Log.d("BackStackEntry", it.toString())
            }
    }
    HomeScreen(
        onNavigateDetail = { navController.navigate(Route.DETAIL.route) },
        onNavigateAlpha = { navController.navigate(Route.ALPHA.route) },
        onNavigateBeta = { navController.navigate(Route.BETA.route) }
    )
}

@Composable
fun HomeScreen(
    onNavigateDetail: () -> Unit,
    onNavigateAlpha: () -> Unit,
    onNavigateBeta: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "201811218 이현우")
        Button(onClick = onNavigateDetail) {
            Text(text = "Go to Detail Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateAlpha) {
            Text(text = "Go to Alpha Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBeta) {
            Text(text = "Go to Beta Screen")
        }
    }
}

@Composable
fun DetailScreen(navController: NavController) {
    recordBackStackEntry(navController = navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detail Screen")
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Go to Home Screen")
        }
        Button(onClick = { navController.navigate(Route.ALPHA.route) }) {
            Text(text = "Go to Alpha Screen")
        }
        Button(onClick = { navController.navigate(Route.BETA.route) }) {
            Text(text = "Go to Beta Screen")
        }
    }
}

@Composable
fun AlphaScreen(navController: NavController) {
    recordBackStackEntry(navController = navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Alpha Screen")
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Go to Home Screen")
        }
    }
}

@Composable
fun BetaScreen(navController: NavController) {
    recordBackStackEntry(navController = navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Beta Screen")
        Button(onClick = {
            navController.navigate(Route.HOME.route) {
                launchSingleTop = true
            }
        }) {
            Text(text = "Go to Home Screen")
        }
    }
}

@Composable
fun GammaScreen(navController: NavController) {

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PreviewNavigationRootScreen() {
    NavigationRootScreen()
}

