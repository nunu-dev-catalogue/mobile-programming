package dev.nunu.mobile.konkuk.navigation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Welcome : Routes("welcome")
    data object Register : Routes("register")
}

@Composable
fun LoginNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(route = Routes.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Routes.Welcome.route + "/{userID}",
            arguments = listOf(
                navArgument(name = "userID") {
                    type = NavType.StringType
                }
            )
        ) {
            WelcomeScreen(
                navController,
                it.arguments?.getString("userID")
            )
        }
        composable(route = Routes.Register.route + "?id={id}&password={password}") { navBackStackEntry ->
            Register(
                navController,
                navBackStackEntry.arguments?.getString("id"),
                navBackStackEntry.arguments?.getString("password")
            )
        }
    }
}