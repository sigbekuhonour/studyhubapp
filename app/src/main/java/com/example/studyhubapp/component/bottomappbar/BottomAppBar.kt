package com.example.studyhubapp.component.bottomappbar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyhubapp.R
import com.example.studyhubapp.component.button.AppButton

@Composable
fun BottomAppBar() {
    BottomAppBar(
        tonalElevation = 2.dp,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        content = {
            AppButton(icon = R.drawable.new_folder)
            Spacer(modifier = Modifier.weight(1f))
            AppButton(icon = R.drawable.new_notes)
        }
    )
}