package com.example.studyhubapp.ui.component.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CreateAccountButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    var createAccountText by remember { mutableStateOf(buttonText) }
    Button(onClick = {
        createAccountText = "Signing you in....."
        onClick()
    }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface)) {
        Text(text = createAccountText, style = MaterialTheme.typography.titleMedium)
    }
}
