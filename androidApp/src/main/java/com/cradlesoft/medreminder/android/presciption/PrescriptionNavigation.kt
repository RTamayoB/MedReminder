package com.cradlesoft.medreminder.android.presciption

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.cradlesoft.medreminder.android.presciption.detail.PrescriptionDetailViewModel
import com.cradlesoft.medreminder.android.presciption.detail.PrescriptionDetailsScreen
import com.cradlesoft.medreminder.android.presciption.list.PrescriptionListViewModel
import com.cradlesoft.medreminder.android.presciption.list.PrescriptionsListScreen
import org.koin.androidx.compose.koinViewModel

const val prescriptionRoute = "prescriptions"

fun NavGraphBuilder.prescriptionGraph(navController: NavController) {
    navigation(
        startDestination = "$prescriptionRoute/list",
        route = prescriptionRoute
    ) {
        composable("$prescriptionRoute/list") {
            val prescriptionListViewModel = koinViewModel<PrescriptionListViewModel>()
            val state = prescriptionListViewModel.state.collectAsState()
            PrescriptionsListScreen(
                state = state.value,
                onEvent = { prescriptionListViewModel.onEvent(it) },
                onNavigateToPrescriptionDetails = {
                    navController.navigate("$prescriptionRoute/details/$it")
                }
            )
        }
        composable(
            route = "$prescriptionRoute/details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val prescriptionDetailViewModel = koinViewModel<PrescriptionDetailViewModel>()
            val state = prescriptionDetailViewModel.state.collectAsState()
            PrescriptionDetailsScreen(
                state = state.value,
                onEvent = { prescriptionDetailViewModel.onEvent(it) },
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}