package org.michaelbel.movies.platform.impl.review

import android.app.Activity
import javax.inject.Inject
import org.michaelbel.movies.platform.review.ReviewService

class ReviewServiceImpl @Inject constructor(): ReviewService {

    override fun requestReview(activity: Activity) {}
}