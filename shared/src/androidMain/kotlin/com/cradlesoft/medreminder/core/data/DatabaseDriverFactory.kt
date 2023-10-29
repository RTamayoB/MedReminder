package com.cradlesoft.medreminder.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.cradlesoft.medreminder.database.PrescriptionsDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            PrescriptionsDatabase.Schema,
            context,
            "prescriptionDatabase.db"
        )
    }
}