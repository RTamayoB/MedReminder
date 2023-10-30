package com.cradlesoft.medreminder.di

import android.content.Context
import com.cradlesoft.medreminder.core.data.DatabaseDriverFactory
import com.cradlesoft.medreminder.core.data.SqlDelightPrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.database.PrescriptionsDatabase

actual class AppModule(
    private val context: Context
) {

    actual val prescriptionsDataSource: PrescriptionsDataSource by lazy {
        SqlDelightPrescriptionsDataSource(
            db = PrescriptionsDatabase(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }
}