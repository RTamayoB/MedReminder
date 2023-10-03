package com.cradlesoft.medreminder.android.calendar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.R
import com.cradlesoft.medreminder.android.calendar.MockDay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarControls(
    previousDate: LocalDateTime,
    selectedDate: LocalDateTime,
) {
    MonthYearSelector(
        previousDate = previousDate,
        selectedDate = selectedDate,
        onDateChanged = { _ ->
        },
        openCalendarDialog = { /*TODO*/ }
    )
    DaysList(selectedDate = selectedDate)
}

@Composable
fun MonthYearSelector(
    previousDate: LocalDateTime,
    selectedDate: LocalDateTime,
    onDateChanged: (LocalDateTime) -> Unit,
    openCalendarDialog: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(Color.Transparent)
    ) {
        IconButton(onClick = openCalendarDialog) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
        }
        Crossfade(
            targetState = selectedDate,
            animationSpec = tween(300),
            label = "",
        ) { currentDate ->
            AnimatedVisibility(
                visible = currentDate != previousDate,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                )
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        IconButton(
            onClick = {
                onDateChanged(selectedDate.minusMonths(1))
            }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }
        IconButton(
            onClick = {
                onDateChanged(selectedDate.plusMonths(1))
            }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun DaysList(
    selectedDate: LocalDateTime,
) {
    val isLeapYear = selectedDate.toLocalDate().isLeapYear
    val days = selectedDate.month.length(isLeapYear)
    val state = rememberLazyListState()
    LaunchedEffect(true) {
        state.animateScrollToItem(selectedDate.dayOfMonth)
    }
    LazyRow(
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier
            .background(Color.Transparent)
            .padding(vertical = 8.dp)
    ) {
        items(days) { currentDay ->
            val date = LocalDate.of(selectedDate.year, selectedDate.month, currentDay + 1)
            val mockDay = MockDay(date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()), date.dayOfMonth.toString())
            DaySelector(
                day = mockDay,
                onDaySelected = {

                },
                modifier = Modifier
                    .padding(horizontal = 1.dp, vertical = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaySelector(
    day: MockDay,
    onDaySelected: (MockDay) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onDaySelected(day)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
            .width(70.dp)
            .height(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(text = day.day, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.weight(1F))
            Text(text = day.dayOfWeek, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }
}