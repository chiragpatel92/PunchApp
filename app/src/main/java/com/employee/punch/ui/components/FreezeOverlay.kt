package com.employee.punch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun FreezeOverlay(buttonBounds: Rect?) {

    if (buttonBounds == null) return
    val density = LocalDensity.current

    val tooltipTop = with(density) { buttonBounds.top - 120.dp.toPx() }
        .coerceAtLeast(0f)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val full = Path().apply {
                    addRect(Rect(0f, 0f, size.width, size.height))
                }
                val cutout = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = buttonBounds.left + 20.dp.toPx(),
                            top = buttonBounds.top,
                            right = buttonBounds.right + 20.dp.toPx(),
                            bottom = buttonBounds.bottom,
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    )
                }
                val spotlight = Path().apply {
                    op(full, cutout, PathOperation.Difference)
                }

                drawPath(
                    path = spotlight,
                    color = Color(0xCC000000)
                )

            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = with(LocalDensity.current) { tooltipTop.toDp() }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Punch Required!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tap Punch Now button below",
                color = Color(0xFFFF8585),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(26.dp))


        }

    }

}