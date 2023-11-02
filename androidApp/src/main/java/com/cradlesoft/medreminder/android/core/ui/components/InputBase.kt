package com.cradlesoft.medreminder.android.core.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun InputBase(
    modifier: Modifier = Modifier,
    label: String? = null,
    input: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        input()
    }
}