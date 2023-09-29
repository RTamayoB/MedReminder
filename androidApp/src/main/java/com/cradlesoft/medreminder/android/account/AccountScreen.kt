package com.cradlesoft.medreminder.android.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cradlesoft.medreminder.android.account.viewmodel.AccountEvent
import com.cradlesoft.medreminder.android.account.viewmodel.AccountState
import com.cradlesoft.medreminder.android.account.viewmodel.Gender
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    accountState: AccountState,
    onEvent: (AccountEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val formTextFieldModifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    val datePickerState = rememberDatePickerState()
    Surface(
        color = Color(0xFFB3EBEE),
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Bienvenido de nuevo Sra. ${accountState.name}",
                color = Color(0xFF280A82),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = formTextFieldModifier
            )
            Spacer(modifier = Modifier.height(80.dp))
            FormTextField(
                value = accountState.name,
                placeholderText = "Nombre del Paciente",
                onValueChange = {
                    onEvent(AccountEvent.OnNameChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            FormTextField(
                value = accountState.lastName,
                placeholderText = "Apellido del Paciente",
                onValueChange = {
                    onEvent(AccountEvent.OnLastNameChanged(it))
                },
                modifier = formTextFieldModifier
            )
            FormDatePicker(
                value = accountState.dateOfBirth,
                placeholderText = "Nombre del Paciente",
                onClick = {
                    onEvent(AccountEvent.EnableSelector(true))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            FormDropdownMenu(
                gender = accountState.gender,
                placeholderText = "Sexo",
                onGenderChange = {
                    onEvent(AccountEvent.OnGenderSelected(it))
                },
                modifier = formTextFieldModifier
            )
        }
    }
    if (accountState.showDateSelector) {
        DatePickerDialog(
            onDismissRequest = { onEvent(AccountEvent.EnableSelector(false)) },
            confirmButton = {
                onEvent(AccountEvent.EnableSelector(false))
                datePickerState.selectedDateMillis?.let {
                    val newDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    onEvent(AccountEvent.OnDateSelected(newDate))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun FormTextField(
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholderText)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
    )
}

@Composable
fun FormDropdownMenu(
    gender: Gender,
    placeholderText: String,
    onGenderChange: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        TextField(
            value = gender.value,
            onValueChange = {},
            placeholder = {
                Text(text = placeholderText)
            },
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        expanded = true
                    }
                )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Gender.values().forEach { genderSelected ->
                DropdownMenuItem(
                    text = { Text(text = genderSelected.value) },
                    onClick = {
                        onGenderChange(genderSelected)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun FormDatePicker(
    value: LocalDate,
    placeholderText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value.format(DateTimeFormatter.ISO_LOCAL_DATE),
        onValueChange = {},
        enabled = false,
        placeholder = {
            Text(text = placeholderText)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.clickable {
            onClick()
        }
    )
}


@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        accountState = AccountState(),
        onEvent = {},
    )
}