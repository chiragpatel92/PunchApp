package com.employee.punch.data.repository

import android.content.Context
import com.employee.punch.data.local.AppDatabase

object RepositoryProvider {

    fun providePunchRepository(context: Context): PunchRepository {
        val db = AppDatabase.getInstance(context)
        return PunchRepository(db.punchDao())
    }
}
