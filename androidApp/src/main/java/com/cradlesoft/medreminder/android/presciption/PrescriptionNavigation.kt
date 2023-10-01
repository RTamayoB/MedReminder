package com.cradlesoft.medreminder.android.presciption

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cradlesoft.medreminder.android.presciption.list.PrescriptionsListScreen

const val prescriptionRoute = "prescriptions"

fun NavGraphBuilder.prescriptionGraph(navController: NavController) {
    navigation(
        startDestination = "$prescriptionRoute/list",
        route = prescriptionRoute
    ) {
        composable("$prescriptionRoute/list") {
            PrescriptionsListScreen(
                onNavigateToPrescriptionDetails = {
                    navController.navigate("$prescriptionRoute/details/$it")
                }
            )
        }
        composable("$prescriptionRoute/details/{id}") {
        }
    }
}