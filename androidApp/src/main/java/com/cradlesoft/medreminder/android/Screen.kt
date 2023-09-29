package com.cradlesoft.medreminder.android

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Progress : Screen("progress", R.string.progress)
    object Prescriptions : Screen("prescriptions", R.string.prescriptions)
    object Documents : Screen("documents", R.string.documents)
    object Profile : Screen("profile", R.string.profile)
}