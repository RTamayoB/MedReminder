package com.cradlesoft.medreminder.android.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.R

@Composable
fun HomeScreen() {
    val mockWeek1 = listOf(
        MockDay("Lun", "18"),
        MockDay("Mar", "19"),
        MockDay("Mie", "20"),
        MockDay("Jue", "21"),
        MockDay("Vie", "22"),
        MockDay("Sab", "23"),
        MockDay("Dom", "24"),
    )
    val mockWeek2 = listOf(
        MockDay("Lun", "25"),
        MockDay("Mar", "26"),
        MockDay("Mie", "27"),
        MockDay("Jue", "28"),
        MockDay("Vie", "29"),
        MockDay("Sab", "30"),
        MockDay("Dom", "1"),
    )
    val mockWeek3 = listOf(
        MockDay("Lun", "2"),
        MockDay("Mar", "3"),
        MockDay("Mie", "4"),
        MockDay("Jue", "5"),
        MockDay("Vie", "6"),
        MockDay("Sab", "7"),
        MockDay("Dom", "8"),
    )
    val mockWeeksData = listOf(
        mockWeek1,
        mockWeek2,
        mockWeek3
    )
    Column {
        HomeScreenHeader(mockWeeksData = mockWeeksData)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Tu siguiente medicamento", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_pill), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Paracetamol")
                Spacer(modifier = Modifier.weight(1F))
                Text(text = "8:00 pm")
            }
        }
        Spacer(modifier = Modifier.weight(1F))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenHeader(
    mockWeeksData: List<List<MockDay>>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .wrapContentHeight()) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(color = Color(0xFF3390E9))
                .fillMaxWidth()
                .height(90.dp)
        )
        Column(
            Modifier.padding(6.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Hola de nuevo", style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Text(text = "Belén!", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekPager(
    mockWeeksData: List<List<MockDay>>
) {
    val state = rememberPagerState(pageCount = { mockWeeksData.size })
    HorizontalPager(state = state) { page ->
        val selectedWeek = mockWeeksData[page]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (day in selectedWeek) {
                DaySelector(
                    day = day,
                    onDaySelected = {

                    },
                    modifier = Modifier
                        .padding(horizontal = 1.dp, vertical = 4.dp)
                )
            }
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
            containerColor = Color(0xFF99DFB2)
        ),
        modifier = modifier
            .width(40.dp)
            .height(50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = day.dayOfWeek, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Text(text = day.day, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class MockDay(
    val dayOfWeek: String,
    val day: String
)