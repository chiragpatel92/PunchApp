package com.employee.punch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DonutChartPlaceholder() {
    Box(
        modifier = Modifier
            .size(160.dp)
            .background(Color(0xFFEDEDED), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text("Weekly Chart")
    }
}
