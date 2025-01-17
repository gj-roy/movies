package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsToolbar(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationIconClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_title),
                modifier = Modifier.testTag("TitleText"),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = modifier.testTag("TopAppBar"),
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier
                    .windowInsetsPadding(displayCutoutWindowInsets)
                    .testTag("BackIconButton")
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
@DevicePreviews
private fun SettingsToolbarPreview() {
    MoviesTheme {
        SettingsToolbar(
            modifier = Modifier.statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}

@Composable
@Preview
private fun SettingsToolbarAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsToolbar(
            modifier = Modifier.statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}