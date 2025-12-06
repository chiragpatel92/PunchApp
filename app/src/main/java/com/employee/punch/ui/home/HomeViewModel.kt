package com.employee.punch.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.employee.punch.data.local.PunchEntity
import com.employee.punch.data.repository.RepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositoryProvider.providePunchRepository(application)

    private val _weeklyCounts = MutableStateFlow<List<Float>>(List(7) { 0f })
    val weeklyCounts: StateFlow<List<Float>> = _weeklyCounts

    init {
        loadWeeklyCounts()
    }

    private fun loadWeeklyCounts() {
        viewModelScope.launch {
            val punches = repo.getAllPunches() // returns all punches (most recent first)
            _weeklyCounts.value = computeLast7DaysCounts(punches)
        }
    }

    private fun computeLast7DaysCounts(punches: List<PunchEntity>): List<Float> {
        val counts = IntArray(7)
        val calNow = Calendar.getInstance()
        calNow.set(Calendar.HOUR_OF_DAY, 0)
        calNow.set(Calendar.MINUTE, 0)
        calNow.set(Calendar.SECOND, 0)
        calNow.set(Calendar.MILLISECOND, 0)
        val startOfToday = calNow.timeInMillis

        for (p in punches) {
            val diff = startOfToday - (p.timestamp - (p.timestamp % 1000))
            val daysAgo = ((startOfToday - p.timestamp) / (24 * 60 * 60 * 1000L)).toInt()
            if (daysAgo in 0..6) {
                counts[daysAgo] += 1
            }
        }
        val list = ArrayList<Float>(7)
        for (i in 6 downTo 0) {
            list.add(counts[i].toFloat())
        }
        return list
    }
}
