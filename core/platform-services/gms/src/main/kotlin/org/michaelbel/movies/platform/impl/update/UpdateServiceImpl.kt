package org.michaelbel.movies.platform.impl.update

import android.app.Activity
import javax.inject.Inject
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

class UpdateServiceImpl @Inject constructor(
    private val inAppUpdate: InAppUpdate
): UpdateService {

    override fun setUpdateAvailableListener(listener: UpdateListener) {
        inAppUpdate.onUpdateAvailableListener = { updateAvailable ->
            listener.onAvailable(updateAvailable)
        }
    }

    override fun startUpdate(activity: Activity) {
        inAppUpdate.startUpdateFlow(activity)
    }
}