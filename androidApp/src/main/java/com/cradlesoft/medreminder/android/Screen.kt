package com.cradlesoft.medreminder.android

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val iconId: Int) {
    object Home: Screen("home", R.string.home, R.drawable.ic_home)
    object Prescriptions: Screen("prescriptions", R.string.prescriptions, R.drawable.ic_calendar)
    object Calendar: Screen("calendar", R.string.calendar, R.drawable.ic_calendar)
    object Documents: Screen("documents", R.string.documents, R.drawable.ic_calendar)
}