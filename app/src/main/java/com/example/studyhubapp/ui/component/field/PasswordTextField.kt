package com.example.studyhubapp.ui.component.field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.studyhubapp.R

@Composable
fun PasswordTextField(
    title: String,
    onTitleChange: (String) -> Unit
) {
    var isPasswordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        value = title,
        shape = MaterialTheme.shapes.medium,
        onValueChange = onTitleChange,
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.titleMedium,
        placeholder = { Text("Password") },
        modifier = Modifier.fillMaxWidth(), trailingIcon = {
            if (isPasswordHidden) {
                Icon(
                    painter = painterResource(id = R.drawable.not_visible),
                    modifier = Modifier.clickable { isPasswordHidden = !isPasswordHidden },
                    contentDescription = null
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.visible),
                    modifier = Modifier.clickable { isPasswordHidden = !isPasswordHidden },
                    contentDescription = null
                )
            }
        }
    )
}