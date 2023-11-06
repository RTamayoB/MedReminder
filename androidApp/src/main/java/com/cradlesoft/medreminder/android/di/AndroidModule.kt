package com.cradlesoft.medreminder.android.di

import com.cradlesoft.medreminder.android.presciption.list.PrescriptionListViewModel
import com.cradlesoft.medreminder.android.presciption.detail.PrescriptionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModel { PrescriptionListViewModel(get()) }
    viewModel { PrescriptionDetailViewModel(get(), get()) }
    viewModelOf(::PrescriptionListViewModel)
    viewModelOf(::PrescriptionDetailViewModel)
}