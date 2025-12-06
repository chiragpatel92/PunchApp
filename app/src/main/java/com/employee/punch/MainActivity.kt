package com.employee.punch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.employee.punch.data.local.UserPrefs
import com.employee.punch.navigation.AppNavGraph
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.theme.EmployeePunchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EmployeePunchTheme {
                val navController = rememberNavController()

                val prefs = UserPrefs(this)
                val startDestination = Routes.LOGIN
                /*val startDestination = if (prefs.isLoggedIn()) {
                    Routes.HOME
                } else {
                    Routes.LOGIN
                }*/

                Scaffold { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
