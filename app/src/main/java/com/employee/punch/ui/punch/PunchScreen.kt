package com.employee.punch.ui.punch

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*

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
        if (!granted) context.showToast("Location permission denied")
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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            ScreenTitle("Punch In")

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (lat != null && lng != null) {

                    val punchLatLng = LatLng(lat!!, lng!!)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(punchLatLng, 19f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),   // Rounded border
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            mapType = MapType.NORMAL
                        )
                    ) {
                        Marker(
                            state = MarkerState(position = punchLatLng),
                            title = "Your Location"
                        )
                    }

                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Fetching location…")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Save Punch",
                enabled = lat != null && lng != null,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    vm.savePunch(lat!!, lng!!) {
                        context.showToast("Punch Saved")
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.PUNCH) { inclusive = true }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
