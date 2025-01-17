package org.michaelbel.movies.ui.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SETTINGS

val Context.appNotificationSettingsIntent: Intent
    get() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= 26 -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            Build.VERSION.SDK_INT >= 21 -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
            }
            else -> {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = "package:$packageName".toUri()
            }
        }
        return intent
    }

fun Activity.resolveNotificationPreferencesIntent() {
    val categories = intent.categories
    if (categories != null && categories.isNotEmpty()) {
        val isCategoryNotificationPreferences = categories.first() == "android.intent.category.NOTIFICATION_PREFERENCES"
        if (isCategoryNotificationPreferences) {
            startActivity(Intent(Intent.ACTION_VIEW, INTENT_ACTION_SETTINGS.toUri()))
        }
    }
}