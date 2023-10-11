package com.cradlesoft.medreminder.android.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cradlesoft.medreminder.android.calendar.components.CalendarContent
import com.cradlesoft.medreminder.android.calendar.components.CalendarControls
import com.cradlesoft.medreminder.calendar.ui.CalendarState
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    state: CalendarState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    state.selectedDateTime
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Calendar") },
                navigationIcon = {
                    //TOD0: Change for icon library
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            CalendarControls(
                previousDate = LocalDateTime.now().minusMonths(1),
                selectedDate = LocalDateTime.now()
            )
            CalendarContent(modifier = Modifier.fillMaxSize())
        }
    }
}

data class MockDay(
    val dayOfWeek: String,
    val day: String
)