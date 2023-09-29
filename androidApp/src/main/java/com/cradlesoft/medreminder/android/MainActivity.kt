package com.cradlesoft.medreminder.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cradlesoft.medreminder.android.account.AccountScreen
import com.cradlesoft.medreminder.android.account.viewmodel.AccountViewModel
import com.cradlesoft.medreminder.android.files.FilesScreen
import com.cradlesoft.medreminder.android.prescription.EditMedicineScreen
import com.cradlesoft.medreminder.android.progress.ProgressScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val destinations = listOf(
        Screen.Progress,
        Screen.Prescriptions,
        Screen.Documents,
        Screen.Profile
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
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
                            Icon(imageVector = Icons.Default.Call, contentDescription = screen.route)
                        },
                        label = {
                            Text(text = stringResource(id = screen.resourceId))
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "progress",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("progress") {
                ProgressScreen()
            }
            composable("prescriptions") {
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
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}
