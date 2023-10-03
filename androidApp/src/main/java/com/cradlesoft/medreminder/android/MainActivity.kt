package com.cradlesoft.medreminder.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cradlesoft.medreminder.android.calendar.CalendarScreen
import com.cradlesoft.medreminder.android.core.theme.MedTheme
import com.cradlesoft.medreminder.android.home.HomeScreen
import com.cradlesoft.medreminder.android.presciption.prescriptionGraph
import com.cradlesoft.medreminder.calendar.ui.CalendarState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val destinations = listOf(
        Screen.Home,
        Screen.Prescriptions,
        Screen.Calendar,
        Screen.Documents,
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconId),
                                contentDescription = stringResource(id = screen.resourceId)
                            )
                        },
                        label = {
                            Text(text = stringResource(id = screen.resourceId), style = MaterialTheme.typography.labelMedium)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen()
            }
            prescriptionGraph(navController)
            composable("calendar") {
                CalendarScreen(
                    state = CalendarState(),
                    onNavigateBack = { /*TODO*/ }
                )
            }
            /*composable("medicines") {
                //TODO: Change for Prescriptions List
                EditMedicineScreen()
            }
            composable("documents") {
                FilesScreen()
            }
            composable("profile") {
                val accountViewModel = viewModel<AccountViewModel>()
                val state by accountViewModel.state.collectAsState()
                AccountScreen(
                    accountState = state,
                    onEvent = { event ->
                        accountViewModel.onEvent(event)
                    }
                )
            }*/
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MedTheme {
        HomeScreen()
    }
}
