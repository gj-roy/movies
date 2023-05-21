package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.VersionPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsVersionBox(
    appVersionData: AppVersionData,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.testTag("ConstraintLayout")
    ) {
        val (icon, version, code) = createRefs()
        createHorizontalChain(icon, version, code, chainStyle = ChainStyle.Packed)

        Icon(
            imageVector = MoviesIcons.MovieFilter,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    width = Dimension.value(24.dp)
                    height = Dimension.value(24.dp)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(version.start)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Icon"),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.settings_app_version_name, appVersionData.version),
            modifier = Modifier
                .constrainAs(version) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(icon.end)
                    top.linkTo(icon.top)
                    end.linkTo(code.start)
                    bottom.linkTo(icon.bottom)
                }
                .padding(start = 4.dp)
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = stringResource(R.string.settings_app_version_code, appVersionData.code),
            modifier = Modifier
                .constrainAs(code) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(version.end)
                    top.linkTo(version.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(version.bottom)
                }
                .padding(start = 2.dp)
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsVersionBoxPreview(
    @PreviewParameter(VersionPreviewParameterProvider::class) appVersionData: AppVersionData
) {
    MoviesTheme {
        SettingsVersionBox(
            appVersionData = appVersionData,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}