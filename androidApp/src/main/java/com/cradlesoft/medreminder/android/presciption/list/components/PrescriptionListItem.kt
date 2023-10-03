package com.cradlesoft.medreminder.android.presciption.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionListItem(
    prescription: UIPrescription,
    modifier: Modifier = Modifier,
    onPrescriptionClicked: (UIPrescription) -> Unit,
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onPrescriptionClicked(prescription)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row {
                Text(text = prescription.name)
                Spacer(modifier = Modifier.weight(1F))
                Icon(imageVector = Icons.Default.Create, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(12.dp))
            ProgressIndicator(
                progress = prescription.progress / 100F,
                modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    var barWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val knobSize = 40.dp
    Box(modifier = modifier) {
        LinearProgressIndicator(
            progress = progress,
            strokeCap = StrokeCap.Round,
            color = Color(0x663610A6),
            modifier = Modifier
                .height(24.dp)
                .onGloballyPositioned {
                    barWidth = it.size.width
                }
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(knobSize)
                .offset(y = (-8).dp)
                .offset(
                    x = with(density) {
                        (progress * barWidth.toDp()) - (knobSize / 2)
                    }
                )
                .clip(CircleShape)
                .background(Color(0xFFB063ED))
        ) {
            Text(
                text = convertToPercentage(progress),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PrescriptionProgressItemPreview() {
    PrescriptionListItem(
        prescription = mockPresctiptions[0],
        onPrescriptionClicked = {}
    )
}

val mockPresctiptions = listOf(
    UIPrescription(
        "Receta Ginecologo",
        50
    ),
    UIPrescription(
        "Receta Dentista",
        50
    ),
    UIPrescription(
        "Receta Medico G.",
        70
    )
)

//TODO: Move to correct package/layer
data class UIPrescription(
    val name: String = "",
    val progress: Int = 0
)

fun convertToPercentage(decimal: Float): String {
    val percentage = (decimal * 100).toInt()
    return "$percentage%"
}