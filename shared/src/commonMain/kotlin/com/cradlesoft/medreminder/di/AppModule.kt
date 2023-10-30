package com.cradlesoft.medreminder.di

import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource

expect class AppModule {
    val prescriptionsDataSource: PrescriptionsDataSource
}