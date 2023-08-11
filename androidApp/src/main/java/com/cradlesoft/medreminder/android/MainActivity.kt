package com.cradlesoft.medreminder.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    val items = listOf<NavItem>(
        NavItem(
            "Progreso",
            androidx.core.R.drawable.ic_call_answer,
        ),
        NavItem(
            "Recetas",
            androidx.core.R.drawable.ic_call_answer,
        ),
        NavItem(
            "Documentos",
            androidx.core.R.drawable.ic_call_answer,
        ),
        NavItem(
            "Perfil",
            androidx.core.R.drawable.ic_call_answer,
        )
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(painter = painterResource(id = item.icon), contentDescription = item.label)
                        },
                        label = {
                            Text(text = item.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = rememberNavController(),
            startDestination = "progress"
        ) {
            composable("progress") {
                ProgressScreen()
            }
        }
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

data class NavItem(
    val label: String,
    @DrawableRes val icon: Int
)

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}
