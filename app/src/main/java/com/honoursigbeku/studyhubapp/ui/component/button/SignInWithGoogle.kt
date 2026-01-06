package com.honoursigbeku.studyhubapp.ui.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState

@Composable
fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    authState: AuthState,
    text: String,
    onClick: () -> Unit,
) {
    val isLoading = authState is AuthState.Loading
    var isSignInWithGoogleButtonClicked by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated || authState is AuthState.Error) {
            isSignInWithGoogleButtonClicked = false
        }
    }
    Button(
        enabled = !isLoading,
        onClick = {
            onClick()
            isSignInWithGoogleButtonClicked = true
        }, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inverseSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.google_icon),
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified,
            contentDescription = ""
        )
        Spacer(
            modifier = Modifier.padding(
                5.dp
            )
        )
        Text(
            text = if (isLoading) "Signing you in " else text,
            style = MaterialTheme.typography.titleMedium
        )
        if (isSignInWithGoogleButtonClicked) {
            CircularProgressIndicator(modifier = Modifier.size(15.dp))
        }
    }
}
