package com.employee.punch.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.components.PrimaryButton
import com.employee.punch.ui.components.PrimaryTextField
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle
import com.employee.punch.util.showToast

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {

    var username by remember { mutableStateOf("") }
    val context = LocalContext.current

    ScreenContainer(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitle("Welcome")

            Spacer(modifier = Modifier.height(60.dp))

            PrimaryTextField(
                value = username,
                onValueChange = { username = it },
                hint = "Enter username"
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Login",
                onClick = {
                    if (username.isBlank()) {
                        context.showToast("Please enter username")
                        return@PrimaryButton
                    }

                    vm.login {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}
