package com.employee.punch.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("employee_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
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
}
