package com.honoursigbeku.studyhubapp.ui.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState

@Composable
fun CreateAccountButton(
    modifier: Modifier = Modifier,
    authState: AuthState,
    buttonText: String,
    onClick: () -> Unit,
) {
    var createButtonText by rememberSaveable { mutableStateOf(buttonText) }
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated || authState is AuthState.Error) {
            createButtonText = buttonText
        }
    }
    Button(
        onClick = {
            createButtonText = "Signing you in"
            onClick()
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface)
    ) {
        Text(
            text = createButtonText, style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.padding(5.dp))
        if (authState is AuthState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(15.dp))
        }
    }
}
