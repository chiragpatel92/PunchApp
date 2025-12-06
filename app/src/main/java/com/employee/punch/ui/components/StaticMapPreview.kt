package com.employee.punch.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.employee.punch.R

@Composable
fun StaticMapPreview(
    lat: Double,
    lng: Double,
    modifier: Modifier = Modifier
) {

    val url =
        "https://maps.googleapis.com/maps/api/staticmap?" +
                "center=$lat,$lng" +
                "&zoom=18" +
                "&size=300x200" +
                "&maptype=roadmap" +
                "&markers=color:red|$lat,$lng" +
                "&key=AIzaSyAu2cViF2uSVAsbt3oHuFVpMnH-4rgqw9o"

    AsyncImage(
        model = url,
        contentDescription = "Punch",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

