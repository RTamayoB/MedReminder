package com.cradlesoft.medreminder.di

import com.cradlesoft.medreminder.core.data.DatabaseDriverFactory
import com.cradlesoft.medreminder.core.data.SqlDelightPrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.database.PrescriptionsDatabase

actual class AppModule {

    actual val prescriptionsDataSource: PrescriptionsDataSource by lazy {
        SqlDelightPrescriptionsDataSource(
            db = PrescriptionsDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }
}