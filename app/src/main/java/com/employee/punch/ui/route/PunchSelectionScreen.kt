package com.employee.punch.ui.route

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.employee.punch.navigation.Routes
import com.employee.punch.ui.components.PrimaryButton
import com.employee.punch.ui.components.ScreenContainer
import com.employee.punch.ui.components.ScreenTitle
import com.employee.punch.ui.components.StaticMapPreview

@Composable
fun PunchSelectionScreen(
    navController: NavController,
    vm: RouteListViewModel = viewModel()
) {

    val punches by vm.punchList.collectAsState()

    val selectedPunchIds = remember { mutableStateListOf<Int>() }

    ScreenContainer {

        ScreenTitle("Select Punches")

        Spacer(modifier = Modifier.height(20.dp))

        if (punches.isEmpty()) {
            Text("No punch-ins found")
            return@ScreenContainer
        }

        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(punches) { punch ->
                    val isChecked = punch.id in selectedPunchIds

                    val animatedBorderColor by animateColorAsState(
                        targetValue = if (isChecked) Color(0xFF3F51B5) else Color(0x33000000),
                        label = ""
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(
                                width = 1.dp,
                                color = animatedBorderColor,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            )
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        StaticMapPreview(
                            lat = punch.latitude,
                            lng = punch.longitude,
                            modifier = Modifier
                                .height(80.dp)
                                .weight(1f)
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Time: ${formatTime(punch.timestamp)}")
                        }

                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) selectedPunchIds.add(punch.id)
                                else selectedPunchIds.remove(punch.id)
                            }
                        )
                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Plot Route",
                enabled = selectedPunchIds.size >= 2,
                onClick = {
                    val ids = selectedPunchIds.joinToString(",")
                    navController.navigate("${Routes.ROUTE}?ids=$ids")
                }
            )
        }
    }
}

fun formatTime(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val format = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return format.format(date)
}
