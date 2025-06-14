package com.example.studyhubapp.component.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun AppButton(modifier: Modifier = Modifier, icon: Int) {
    IconButton(onClick = {}) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}