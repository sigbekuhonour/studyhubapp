package com.example.studyhubapp.ui.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studyhubapp.R

@Composable
fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    var createAccountText by remember { mutableStateOf(text) }
    Button(onClick = {
        createAccountText = "Signing you in....."
        onClick()
    }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inverseSurface)) {
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
        Text(text = createAccountText, style = MaterialTheme.typography.titleMedium)
    }
}
