package com.cradlesoft.medreminder.android.files

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Documentos")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.UploadFile, contentDescription = "Upload File")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB3EBEE)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(mockFiles) { file ->
                FileItem(file = file)
                Divider()
            }
        }
    }
}

@Composable
fun FileItem(file: File) {
    ListItem(
        headlineContent = { Text(text = file.fileName) },
        supportingContent = {
            Row {
                Text(text = file.fileSize)
                Spacer(modifier = Modifier.weight(1F))
                Text(text = file.fileDate)
            }
        },
        leadingContent = {
            Icon(imageVector = Icons.Default.Description, contentDescription = "logo")
        }
    )
}

//TODO: Move file model to domain layer and fix value types
data class File(
    val fileName: String,
    val fileSize: String,
    val fileDate: String
)

val mockFiles = listOf(
    File("Receta Doctor Adonis", "465 kB", "11/2/2023"),
    File("prescription_Hospital_Santa_Marta", "170 kB", "18/8/2023"),
    File("Medicina infeccion estomago", "149 kB", "31/8/2022"),
    File("2022-12-8", "225 kB", "12/8/2022")
)

@Preview
@Composable
fun FilesScreenPreview() {
    FilesScreen()
}