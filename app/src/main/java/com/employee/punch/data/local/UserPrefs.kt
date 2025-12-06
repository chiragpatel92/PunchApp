package com.employee.punch.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("employee_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_LAST_PUNCH_TIME = "last_punch_time"
    }

    fun setLoggedIn(value: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout() {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
    }

    fun setLastPunchTime(time: Long) {
        prefs.edit().putLong(KEY_LAST_PUNCH_TIME, time).apply()
    }

    fun getLastPunchTime(): Long {
        return prefs.getLong(KEY_LAST_PUNCH_TIME, 0L)
    }


}
