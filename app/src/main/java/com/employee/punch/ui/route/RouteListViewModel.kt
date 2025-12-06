package com.employee.punch.ui.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.employee.punch.data.local.PunchEntity
import com.employee.punch.data.repository.RepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RouteListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryProvider.providePunchRepository(application)

    private val _punchList = MutableStateFlow<List<PunchEntity>>(emptyList())
    val punchList: StateFlow<List<PunchEntity>> = _punchList

    init {
        loadPunches()
    }

    private fun loadPunches() {
        viewModelScope.launch {
            _punchList.value = repository.getAllPunches()
        }
    }
}
