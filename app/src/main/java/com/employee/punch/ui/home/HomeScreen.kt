package com.employee.punch.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.data.local.UserPrefs
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.components.DonutChartReal
import com.employee.punch.ui.components.FreezeOverlay
import com.employee.punch.ui.components.PrimaryButton
import com.employee.punch.ui.theme.PrimaryDark
import com.employee.punch.util.PunchTimerManager

@Composable
fun HomeScreen(
    navController: NavController,
    vm: HomeViewModel = viewModel()
) {
    val prefs = UserPrefs(LocalContext.current)

    val remaining by PunchTimerManager.remainingTime.collectAsState()
    val isWarning by PunchTimerManager.isWarning.collectAsState()
    val isPunchRequired by PunchTimerManager.isPunchRequired.collectAsState()

    val weekly by vm.weeklyCounts.collectAsState()

    BackHandler {
        (navController.context as? android.app.Activity)?.finish()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Logout",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        prefs.logout()
                        PunchTimerManager.stopTimer()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                )
            }

            HomeContent(
                navController = navController,
                remaining = remaining,
                isWarning = isWarning,
                weekly = weekly
            )
        }

        if (isPunchRequired) {
            FreezeOverlay(navController)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController,
    remaining: Long,
    isWarning: Boolean,
    weekly: List<Float>
) {
    val minutes = (remaining / 1000) / 60
    val seconds = (remaining / 1000) % 60
    val highlightColor =
        if (isWarning) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        item {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Next Punch In",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = "%02d:%02d".format(minutes, seconds),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        ),
                        color = highlightColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Weekly Activity",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    DonutChartReal(values = weekly)
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }

        item {

            PrimaryButton(
                text = "Punch Now",
                icon = Icons.Default.PlayArrow,
                onClick = { navController.navigate(Routes.PUNCH) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "View Route",
                icon = Icons.Default.LocationOn,
                containerColor = Color.White,
                contentColor = PrimaryDark,
                onClick = { navController.navigate(Routes.SELECT_PUNCHES) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



