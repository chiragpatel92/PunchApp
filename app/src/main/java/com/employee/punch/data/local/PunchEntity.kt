package com.employee.punch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "punch_table")
data class PunchEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)
