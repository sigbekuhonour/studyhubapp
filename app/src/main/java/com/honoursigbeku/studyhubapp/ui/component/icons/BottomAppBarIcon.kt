package com.honoursigbeku.studyhubapp.ui.component.icons

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BottomAppBarIcon(modifier: Modifier = Modifier, icon: Int, onClick: () -> Unit = {}) {
    Icon(
        painter = painterResource(id = icon),
        tint = MaterialTheme.colorScheme.scrim,
        modifier = Modifier.clickable { onClick() },
        contentDescription = null
    )
}