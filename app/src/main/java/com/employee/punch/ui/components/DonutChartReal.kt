package com.employee.punch.ui.components

import android.content.Context
import android.graphics.Color as AndroidColor
import android.view.ViewGroup
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.employee.punch.ui.theme.ChartBlue
import com.employee.punch.ui.theme.ChartGreen
import com.employee.punch.ui.theme.ChartYellow
import com.employee.punch.ui.theme.ChartRed
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

/**
 * values: list of floats for segments (order preserved)
 * labels: optional labels corresponding to values (same size)
 * size: chart size
 */
@Composable
fun DonutChartReal(
    values: List<Float>,
    labels: List<String>? = null,
    modifier: Modifier = Modifier,
    size: Dp = 160.dp
) {
    val ctx = LocalContext.current

    val colorInts = intArrayOf(
        ChartBlue.toArgb(),
        ChartGreen.toArgb(),
        ChartYellow.toArgb(),
        ChartRed.toArgb()
    )

    AndroidView(
        modifier = modifier.size(size),
        factory = { context ->
            PieChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                description.isEnabled = false
                setUsePercentValues(false)
                isDrawHoleEnabled = true
                holeRadius = 70f
                setHoleColor(AndroidColor.TRANSPARENT)
                setEntryLabelColor(AndroidColor.BLACK)
                setEntryLabelTextSize(12f)
                setDrawEntryLabels(false) // we handle labels with legend or center text
                setDrawCenterText(true)
                setCenterTextSize(14f)
                setCenterTextColor(AndroidColor.DKGRAY)
                legend.isEnabled = false
                setTouchEnabled(false)
            }
        },
        update = { chart ->
            val entries = ArrayList<PieEntry>()
            for (i in values.indices) {
                entries.add(PieEntry(values[i], labels?.getOrNull(i) ?: ""))
            }

            val dataSet = PieDataSet(entries, "").apply {
                colors = List(entries.size) { idx ->
                    colorInts[idx % colorInts.size]
                }
                sliceSpace = 2f
                selectionShift = 6f
                valueTextSize = 12f
                valueTextColor = AndroidColor.WHITE
            }

            val data = PieData(dataSet)
            chart.data = data

            val total = values.sum().toInt()
            chart.centerText = "Total\n$total"
            chart.invalidate()
        }
    )
}
