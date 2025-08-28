package com.openknights.app.feature.project.projectdetail.component

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.openknights.app.core.ui.KnightsTopAppBar
import com.openknights.app.core.ui.TopAppBarNavigationType
import com.openknights.app.core.designsystem.color.KnightsColor
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.feature.project.R

import androidx.compose.ui.res.stringResource

@Composable
internal fun ProjectDetailTopAppBar(
    onBackClick: () -> Unit,
) {
    KnightsTopAppBar(
        title = stringResource(id = R.string.project_detail_screen_title),
        navigationIconContentDescription = null,
        navigationType = TopAppBarNavigationType.Back,
        onNavigationClick = onBackClick,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ProjectDetailTopAppBarPreview() {
    KnightsTheme {
        ProjectDetailTopAppBar(
            onBackClick = { }
        )
    }
}