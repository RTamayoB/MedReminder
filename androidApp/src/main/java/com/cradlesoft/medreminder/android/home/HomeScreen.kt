package com.cradlesoft.medreminder.android.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cradlesoft.medreminder.android.R

@Composable
fun HomeScreen() {

    Column {
        HomeScreenHeader()
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

@Composable
fun HomeScreenHeader(
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