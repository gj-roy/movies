package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import org.michaelbel.movies.network.model.ImagesResponse
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class KtorMovieService @Inject constructor(
    private val ktorHttpClient: HttpClient
) {

    suspend fun movies(
        list: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return ktorHttpClient.get("movie/$list") {
            parameter("language", language)
            parameter("page", page)
        }.body()
    }

    suspend fun movie(
        movieId: Int,
        language: String
    ): Movie {
        return ktorHttpClient.get("movie/$movieId") {
            parameter("language", language)
        }.body()
    }

    suspend fun images(
        movieId: Int
    ): ImagesResponse {
        return ktorHttpClient.get("movie/$movieId/images").body()
    }
}