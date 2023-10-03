package com.cradlesoft.medreminder.android.calendar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
        )
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                for (d in 1 until 10) {
                    stickyHeader {
                        HourHeader(
                            hour = LocalTime.now(),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    items(2) {
                        CalendarItem(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HourHeader(
    hour: LocalTime,
    modifier: Modifier = Modifier
) {
    val header = hour.format(DateTimeFormatter.ofPattern("HH:mm a"))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(text = header, style = MaterialTheme.typography.bodyMedium)
        Divider()
    }
}

@Composable
fun CalendarItem(
    modifier: Modifier = Modifier
) {
    var open by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(
                            RoundedCornerShape(30.dp)
                        )
                )
                Column {
                    Text(text = "Paracetamol", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text(text = "1 Tableta - 80mg", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1F))
                TextButton(
                    onClick = { open = !open },
                ) {
                    Text(text = "...")
                }
            }
            AnimatedVisibility(visible = open) {
                Divider()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Pasar")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Tomar")
                    }
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Mover")
                    }
                }
            }
        }
    }
}