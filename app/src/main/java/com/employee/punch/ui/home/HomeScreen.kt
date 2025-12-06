package com.employee.punch.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.components.DonutChartReal
import com.employee.punch.ui.components.FreezeOverlay
import com.employee.punch.ui.components.PrimaryButton
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle
import com.employee.punch.util.PunchTimerManager

@Composable
fun HomeScreen(navController: NavController, vm: HomeViewModel = viewModel()) {

    val remaining by PunchTimerManager.remainingTime.collectAsState()
    val isWarning by PunchTimerManager.isWarning.collectAsState()
    val isPunchRequired by PunchTimerManager.isPunchRequired.collectAsState()

    val weekly by vm.weeklyCounts.collectAsState()

    ScreenContainer {

        // Title
        ScreenTitle("Dashboard")

        Spacer(modifier = Modifier.height(16.dp))

        // Countdown Timer
        val minutes = (remaining / 1000) / 60
        val seconds = (remaining / 1000) % 60
        val textColor = if (isWarning) Color(0xFFFF9800) else MaterialTheme.colorScheme.onBackground

        Text(
            text = "Next punch in: %02d:%02d".format(minutes, seconds),
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Donut chart with weekly data (oldest -> newest)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            DonutChartReal(values = weekly)
        }

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Punch Now",
            onClick = { navController.navigate(Routes.PUNCH) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            text = "View Route",
            onClick = { navController.navigate(Routes.SELECT_PUNCHES) }
        )

        if (isPunchRequired) {
            FreezeOverlay()
        }
    }
}
