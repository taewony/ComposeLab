package com.openknights.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable (() -> Unit))? = null,
    actions: @Composable RowScope.() -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor,
            actionIconContentColor = contentColor,
            navigationIconContentColor = contentColor
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun OpenTopBarPreview_Back() {
    OpenTopBar(
        title = "OpenKnights",
        navigationIcon = {
            IconButton(onClick = { /* Back */ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun OpenTopBarPreview_CloseAndAction() {
    OpenTopBar(
        title = "설정",
        navigationIcon = {
            IconButton(onClick = { /* Close */ }) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        },
        actions = {
            IconButton(onClick = { /* Save */ }) {
                Icon(Icons.Filled.Save, contentDescription = "Save")
            }
        }
    )
}
