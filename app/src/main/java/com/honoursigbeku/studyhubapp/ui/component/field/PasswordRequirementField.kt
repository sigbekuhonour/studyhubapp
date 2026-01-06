package com.honoursigbeku.studyhubapp.ui.component.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.honoursigbeku.studyhubapp.R


@Composable
fun PasswordRequirementField(password: String, modifier: Modifier = Modifier) {
    val hasMinLength = password.length >= 6
    val hasNumber = password.any { it.isDigit() }
    val hasUpper = password.any { it.isUpperCase() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Password requirements include the following:")
        RequirementRow("1. 6+ characters", hasMinLength)
        RequirementRow("2. At least 1 number", hasNumber)
        RequirementRow("3. At least 1 uppercase", hasUpper)
        RequirementRow(
            "4. At least one special character \n (e.g '@', '#', '$', '&', '%', '*') ",
            hasSpecialChar
        )
    }
}

@Composable
fun RequirementRow(text: String, hasMetRequirement: Boolean) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        Text(text = text)
        if (hasMetRequirement) {
            Icon(
                painter = painterResource(R.drawable.encrypted),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.not_encrypted),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}