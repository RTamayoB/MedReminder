package com.cradlesoft.medreminder.android.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IntegerInputStepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    allowNegatives: Boolean = false
) {
    InputStepperBase(
        value = value,
        onValueChange = {
            onValueChange(it.toInt())
        },
        modifier = modifier,
        textFieldModifier = textFieldModifier,
        label = label,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        allowNegatives = allowNegatives
    )
}

@Composable
fun FloatInputStepper(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    allowNegatives: Boolean = false
) {
    InputStepperBase(
        value = value,
        onValueChange = {
            onValueChange(it.toFloat())
        },
        modifier = modifier,
        textFieldModifier = textFieldModifier,
        label = label,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        allowNegatives = allowNegatives
    )
}

@Composable
fun LongInputStepper(
    value: Long,
    onValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    allowNegatives: Boolean = false
) {
    InputStepperBase(
        value = value,
        onValueChange = {
            onValueChange(it.toLong())
        },
        modifier = modifier,
        textFieldModifier = textFieldModifier,
        label = label,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        allowNegatives = allowNegatives
    )
}

@Composable
internal fun InputStepperBase(
    value: Number,
    onValueChange: (Number) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    allowNegatives: Boolean = false
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    val borderColor = animateColorAsState(
        targetValue = if (isFocused) MaterialTheme.colorScheme.primary else  MaterialTheme.colorScheme.outline,
        label = ""
    )
    val thickness = animateDpAsState(targetValue = if (isFocused) 2.dp else 1.dp, label = "")
    InputBase(
        modifier = modifier,
        label = label
    ) {
        Row {
            OutlinedTextField(
                value = value.toString(),
                onValueChange = {
                    onValueChange(it.toFloat())
                },
                trailingIcon = trailingIcon,
                keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
                modifier = textFieldModifier.onFocusChanged {
                    isFocused = it.isFocused
                }
            )
            Column(
                modifier = Modifier
                    .height(TextFieldDefaults.MinHeight)
                    .width(48.dp)
                    .border(
                        thickness.value,
                        borderColor.value,
                        RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                    )
                    .clip(
                        RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                    )
            ) {
                IconButton(
                    onClick = {
                        onValueChange(value.toFloat().plus(1))
                    },
                    modifier = Modifier.height(TextFieldDefaults.MinHeight / 2)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Increase Value",
                        modifier = Modifier.height(TextFieldDefaults.MinHeight / 2)
                    )
                }
                Divider(color = borderColor.value, thickness = thickness.value)
                IconButton(
                    onClick = {
                        if (value.toFloat().minus(1) < 0) {
                            if (allowNegatives) {
                                onValueChange(value.toFloat().minus(1))
                            }
                        } else {
                            onValueChange(value.toFloat().minus(1))
                        }
                    },
                    modifier = Modifier.height(TextFieldDefaults.MinHeight / 2)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Decrease Value",
                        modifier = Modifier.height(TextFieldDefaults.MinHeight / 2)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun IntegerInputStepperPreview() {
    var inputValue by remember {
        mutableIntStateOf(1)
    }
    IntegerInputStepper(
        value = inputValue,
        textFieldModifier = Modifier.width(60.dp),
        onValueChange = {
            inputValue = it
        }
    )
}

@Preview
@Composable
fun FloatInputStepperPreview() {
    var inputValue by remember {
        mutableFloatStateOf(1.0F)
    }
    FloatInputStepper(
        value = inputValue,
        textFieldModifier = Modifier.width(60.dp),
        onValueChange = {
            inputValue = it
        }
    )
}
