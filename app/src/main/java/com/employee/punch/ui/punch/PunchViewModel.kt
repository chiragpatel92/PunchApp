package com.employee.punch.ui.punch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.employee.punch.data.local.AppDatabase
import com.employee.punch.data.local.PunchEntity
import com.employee.punch.data.repository.RepositoryProvider
import com.employee.punch.util.PunchTimerManager
import kotlinx.coroutines.launch

class PunchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryProvider.providePunchRepository(application)

    fun savePunch(lat: Double, lng: Double, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.savePunch(
                lat = lat,
                lng = lng,
                time = System.currentTimeMillis()
            )

            // Reset timer after punch
            PunchTimerManager.resetTimer()
            onSuccess()
        }
    }
}
