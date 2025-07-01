package com.example.studyhubapp.component.topappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    topAppBarText: String,
    actions: @Composable RowScope.() -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                color = MaterialTheme.colorScheme.scrim,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun ActionsComp() {
    var isEnabled by rememberSaveable { mutableStateOf(false) }
    var actionText by rememberSaveable { mutableStateOf("Edit") }

    Button(onClick = {

    }, colors = ButtonDefaults.buttonColors(Color.Transparent)) {
        Row {
            Text(
                text = actionText,
                color = MaterialTheme.colorScheme.scrim,
                fontSize = 20.sp
            )
        }
    }
}