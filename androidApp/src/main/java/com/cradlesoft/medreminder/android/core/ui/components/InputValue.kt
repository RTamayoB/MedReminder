package com.cradlesoft.medreminder.android.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Used to show value of an input in a details page
 * @param value The value to show on the box
 * @param modifier The modifier to be applied on the composable
 * @param label The label to be above the box
 * @param textAlignment The alignment on the text inside the box
 */
@Composable
fun InputValue(
    value: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    textAlignment: Alignment = Alignment.CenterStart
) {
    InputBase(
        label = label,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = OutlinedTextFieldDefaults.MinWidth,
                    minHeight = OutlinedTextFieldDefaults.MinHeight
                )
                .clip(OutlinedTextFieldDefaults.shape)
                .border(
                    width = (OutlinedTextFieldDefaults.UnfocusedBorderThickness),
                    color = MaterialTheme.colorScheme.outline,
                    shape = OutlinedTextFieldDefaults.shape
                ),
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .padding(14.dp)
                    .align(textAlignment)
            )
        }
    }
}

@Preview
@Composable
fun InputValuePreview() {
    InputValue(value = "Example")
}