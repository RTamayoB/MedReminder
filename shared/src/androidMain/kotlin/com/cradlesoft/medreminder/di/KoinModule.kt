package com.cradlesoft.medreminder.di

import com.cradlesoft.medreminder.core.data.DatabaseDriverFactory
import com.cradlesoft.medreminder.core.data.SqlDelightPrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.database.PrescriptionsDatabase
import org.koin.dsl.module

val prescriptionsDataSourceModule = module {
    factory<PrescriptionsDataSource> { SqlDelightPrescriptionsDataSource(PrescriptionsDatabase(driver = DatabaseDriverFactory(get()).create())) }
}