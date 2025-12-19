package com.honoursigbeku.studyhubapp.ui.component.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import com.honoursigbeku.studyhubapp.R


@Composable
fun EmailTextField(
    title: String,
    onTitleChange: (String) -> Unit
) {
    TextField(
        value = title,
        shape = MaterialTheme.shapes.large,
        onValueChange = onTitleChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.mail),
                contentDescription = null
            )
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.titleMedium,
        placeholder = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentType = ContentType.EmailAddress }
    )
}