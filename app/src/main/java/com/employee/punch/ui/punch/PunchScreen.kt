package com.employee.punch.ui.punch

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.components.PrimaryButton
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle
import com.employee.punch.util.LocationHelper
import com.employee.punch.util.showToast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun PunchScreen(navController: NavController, vm: PunchViewModel = viewModel()) {

    val context = LocalContext.current

    var lat by remember { mutableStateOf<Double?>(null) }
    var lng by remember { mutableStateOf<Double?>(null) }

    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (!granted) {
            context.showToast("Location permission denied")
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            val loc = LocationHelper.getCurrentLocation(context)
            if (loc != null) {
                lat = loc.first
                lng = loc.second
            } else {
                context.showToast("Failed to get location")
            }
        }
    }

    ScreenContainer {

        ScreenTitle("Punch-In")

        Spacer(modifier = Modifier.height(30.dp))

        if (lat != null && lng != null) {
            Text("Lat: $lat")
            Text("Lng: $lng")

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Save Punch",
                onClick = {
                    vm.savePunch(lat!!, lng!!) {
                        context.showToast("Punch Saved")
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.PUNCH) { inclusive = true }
                        }
                    }
                }
            )

        } else {
            Text("Fetching location…")
        }
    }
}

