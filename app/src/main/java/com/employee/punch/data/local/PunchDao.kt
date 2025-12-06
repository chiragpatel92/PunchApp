package com.employee.punch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PunchDao {

    @Insert
    suspend fun insertPunch(punch: PunchEntity)

    @Query("SELECT * FROM punch_table ORDER BY timestamp DESC")
    suspend fun getAllPunches(): List<PunchEntity>

    @Query("SELECT * FROM punch_table ORDER BY timestamp ASC")
    suspend fun getPunchesForRoute(): List<PunchEntity>
}
