package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import org.michaelbel.movies.network.model.Account

class KtorAccountService @Inject constructor(
    private val ktorHttpClient: HttpClient
) {

    suspend fun accountDetails(
        sessionId: String
    ): Account {
        return ktorHttpClient.get("account") {
            parameter("session_id", sessionId)
        }.body()
    }
}