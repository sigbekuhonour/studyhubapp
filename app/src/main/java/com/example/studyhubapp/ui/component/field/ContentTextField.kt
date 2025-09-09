package com.example.studyhubapp.ui.component.field

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentTextField(
    content: String? = "",
    onContentChange: (String) -> Unit
) {
    if (content != null) {
        TextField(
            value = content,
            onValueChange = onContentChange,
            singleLine = false,
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.labelSmall,
            placeholder = { Text("Contents...") },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}