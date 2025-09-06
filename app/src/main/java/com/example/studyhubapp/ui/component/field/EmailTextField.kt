package com.example.studyhubapp.ui.component.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun EmailTextField(
    title: String,
    onTitleChange: (String) -> Unit
) {
    TextField(
        value = title,
        shape = MaterialTheme.shapes.large,
        onValueChange = onTitleChange,
        leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
        singleLine = true,
        textStyle = MaterialTheme.typography.titleMedium,
        placeholder = { Text("Email") },
        modifier = Modifier.fillMaxWidth()
    )
}