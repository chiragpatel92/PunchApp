package com.employee.punch.ui.route

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun RouteScreen(
    navController: NavController,
    ids: String,
    vm: RouteMapViewModel = viewModel()
) {

    val idList = ids.split(",").filter { it.isNotEmpty() }.map { it.toInt() }

    LaunchedEffect(Unit) {
        vm.loadPunchesByIds(idList)
    }

    val punches by vm.punches.collectAsState()

    ScreenContainer {

        ScreenTitle("Route Map")

        Spacer(modifier = Modifier.height(20.dp))

        if (punches.size < 2) return@ScreenContainer

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(punches.first().latitude, punches.first().longitude),
                15f
            )
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = MapType.NORMAL
            )
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
