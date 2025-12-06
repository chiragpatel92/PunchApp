package com.employee.punch.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.employee.punch.data.local.PunchEntity
import com.employee.punch.data.repository.RepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RouteMapViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositoryProvider.providePunchRepository(application)

    private val _punches = MutableStateFlow<List<PunchEntity>>(emptyList())
    val punches = _punches.asStateFlow()

    fun loadPunchesByIds(ids: List<Int>) {
        viewModelScope.launch {
            val all = repo.getAllPunches()
            _punches.value = all.filter { ids.contains(it.id) }
        }
    }
}
