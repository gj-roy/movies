package org.michaelbel.movies.settings.ui

import android.app.GrammaticalInflectionManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.genderText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsGenderDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("ConfirmTextButton")
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    modifier = Modifier.testTag("ConfirmText"),
                    style = MaterialTheme.typography.labelLarge.copy(MaterialTheme.colorScheme.primary)
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(MoviesIcons.Cat),
                contentDescription = stringResource(MoviesContentDescription.AppearanceIcon),
                modifier = Modifier.testTag("Icon")
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings_gender),
                modifier = Modifier.testTag("Title"),
                style = MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.onSurface)
            )
        },
        text = {
            SettingsGenderDialogContent(
                onDialogDismiss = { onDismissRequest() },
                modifier = Modifier.testTag("Content")
            )
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun SettingsGenderDialogContent(
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val grammaticalInflectionManager by remember { mutableStateOf(context.getSystemService(GrammaticalInflectionManager::class.java)) }
    val grammaticalGender by remember { mutableStateOf(grammaticalInflectionManager.applicationGrammaticalGender) }
    val currentGrammaticalGender by remember { mutableStateOf(GrammaticalGender.transform(grammaticalGender)) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        GrammaticalGender.VALUES.forEach { gender ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clickable {
                        grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(gender.value)
                        onDialogDismiss()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentGrammaticalGender == gender,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = gender.genderText,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingsGenderDialogPreview() {
    MoviesTheme {
        SettingsGenderDialog(
            onDismissRequest = {}
        )
    }
}

@Composable
@Preview
private fun SettingsGenderDialogAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsGenderDialog(
            onDismissRequest = {}
        )
    }
}