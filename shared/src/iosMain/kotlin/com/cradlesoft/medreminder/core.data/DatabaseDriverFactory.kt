package com.cradlesoft.medreminder.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.cradlesoft.medreminder.database.PrescriptionsDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(PrescriptionsDatabase.Schema, "prescriptionDatabase.db")
    }
}