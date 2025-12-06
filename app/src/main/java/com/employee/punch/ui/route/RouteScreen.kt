package com.employee.punch.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle

@Composable
fun RouteScreen(navController: NavController, ids: String, vm: RouteMapViewModel = viewModel()) {

    val idList = ids.split(",").filter { it.isNotEmpty() }.map { it.toInt() }

    LaunchedEffect(Unit) {
        vm.loadPunchesByIds(idList)
    }

    val punches by vm.punches.collectAsState()

    ScreenContainer {

        ScreenTitle("Route Map")

        if (punches.size < 2) return@ScreenContainer

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(punches.first().latitude, punches.first().longitude),
                15f
            )
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            punches.forEach { punch ->
                Marker(
                    state = MarkerState(
                        position = LatLng(punch.latitude, punch.longitude)
                    ),
                    title = "Punch at ${punch.timestamp}"
                )
            }

            Polyline(
                points = punches.map { LatLng(it.latitude, it.longitude) },
                color = Color.Blue,
                width = 10f
            )
        }
    }
}
