package com.employee.punch.data.repository

import com.employee.punch.data.local.PunchDao
import com.employee.punch.data.local.PunchEntity

class PunchRepository(private val dao: PunchDao) {

    suspend fun savePunch(lat: Double, lng: Double, time: Long) {
        dao.insertPunch(PunchEntity(latitude = lat, longitude = lng, timestamp = time))
    }

    suspend fun getAllPunches() = dao.getAllPunches()

    suspend fun getPunchRoute() = dao.getPunchesForRoute()
}
