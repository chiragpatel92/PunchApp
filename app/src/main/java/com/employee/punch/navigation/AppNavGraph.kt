package com.employee.punch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.employee.punch.ui.home.HomeScreen
import com.employee.punch.ui.login.LoginScreen
import com.employee.punch.ui.punch.PunchScreen
import com.employee.punch.ui.route.PunchSelectionScreen
import com.employee.punch.ui.route.RouteScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.PUNCH) {
            PunchScreen(navController)
        }
        composable(
            route = Routes.ROUTE + "?ids={ids}",
            arguments = listOf(
                navArgument("ids") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val ids = backStackEntry.arguments?.getString("ids") ?: ""
            RouteScreen(navController, ids)
        }
        composable(Routes.SELECT_PUNCHES) {
            PunchSelectionScreen(navController)
        }
    }
}
