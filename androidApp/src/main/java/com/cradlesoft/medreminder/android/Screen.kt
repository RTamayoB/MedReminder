package com.cradlesoft.medreminder.android

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home: Screen("home", R.string.home)
    object Prescriptions: Screen("prescriptions", R.string.prescriptions)
    object Drugs: Screen("drugs", R.string.drugs)
    object Appointments: Screen("appointments", R.string.appointments)
    object Documents: Screen("documents", R.string.documents)
}