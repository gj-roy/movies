package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.ktx.isEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.compose.iconbutton.SearchIcon
import org.michaelbel.movies.ui.compose.iconbutton.SettingsIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeSmall
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun FeedToolbar(
    title: String,
    account: AccountDb,
    onSearchIconClick: () -> Unit,
    onAuthIconClick: () -> Unit,
    onAccountIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        },
        modifier = modifier,
        navigationIcon = {
            SearchIcon(
                onClick = onSearchIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
            )
        },
        actions = {
            Row(
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
            ) {
                SettingsIcon(
                    onClick = onSettingsIconClick
                )

                IconButton(
                    onClick = if (account.isEmpty) onAuthIconClick else onAccountIconClick
                ) {
                    if (account.isEmpty) {
                        Image(
                            imageVector = MoviesIcons.AccountCircle,
                            contentDescription = stringResource(MoviesContentDescription.AccountIcon),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    } else {
                        AccountAvatar(
                            account = account,
                            fontSize = account.lettersTextFontSizeSmall,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
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
private fun FeedToolbarPreview() {
    MoviesTheme {
        FeedToolbar(
            title = stringResource(R.string.feed_title_now_playing),
            account = AccountDb.Empty,
            onSearchIconClick = {},
            onAccountIconClick = {},
            onAuthIconClick = {},
            onSettingsIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Composable
@Preview
private fun FeedToolbarAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        FeedToolbar(
            title = stringResource(R.string.feed_title_now_playing),
            account = AccountDb.Empty,
            onSearchIconClick = {},
            onAccountIconClick = {},
            onAuthIconClick = {},
            onSettingsIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}