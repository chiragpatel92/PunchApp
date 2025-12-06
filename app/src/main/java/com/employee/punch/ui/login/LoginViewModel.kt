package com.employee.punch.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.employee.punch.data.local.UserPrefs

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPrefs(application)

    fun login(onLoggedIn: () -> Unit) {
        prefs.setLoggedIn(true)
        onLoggedIn()
    }
}
