package com.employee.punch.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.employee.punch.ui.theme.TextPrimary

@Composable
fun ScreenTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = modifier
    )
}
