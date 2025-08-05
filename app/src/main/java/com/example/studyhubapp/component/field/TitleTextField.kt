package com.example.studyhubapp.component.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TitleTextField(
    title: String,
    onTitleChange: (String) -> Unit
) {
    TextField(
        value = title,
        onValueChange = onTitleChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.titleMedium,
        placeholder = { Text("Title") },
        modifier = Modifier.fillMaxWidth()
    )
}