package com.employee.punch.util

import android.content.Context
import com.employee.punch.data.local.UserPrefs
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object PunchTimerManager {

    private const val PUNCH_INTERVAL = 10 * 60 * 1000L
    private const val WARNING_TIME = 9 * 60 * 1000L

    private var prefs: UserPrefs? = null
    private var timerJob: Job? = null

    private val _remainingTime = MutableStateFlow(0L)
    val remainingTime = _remainingTime.asStateFlow()

    private val _isWarning = MutableStateFlow(false)
    val isWarning = _isWarning.asStateFlow()

    private val _isPunchRequired = MutableStateFlow(false)
    val isPunchRequired = _isPunchRequired.asStateFlow()

    fun init(context: Context) {
        prefs = UserPrefs(context)
        startTimer()
    }

    fun startTimer() {
        timerJob?.cancel()

        val lastPunch = prefs?.getLastPunchTime() ?: 0L

        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                val elapsed = System.currentTimeMillis() - lastPunch
                val remaining = PUNCH_INTERVAL - elapsed

                _remainingTime.value = remaining.coerceAtLeast(0L)

                _isWarning.value = elapsed >= WARNING_TIME && elapsed < PUNCH_INTERVAL

                _isPunchRequired.value = elapsed >= PUNCH_INTERVAL

                delay(1000L)
            }
        }
    }

    fun resetTimer() {
        prefs?.setLastPunchTime(System.currentTimeMillis())
        startTimer()
    }

    fun stopTimer() {
        timerJob?.cancel()
    }
}
