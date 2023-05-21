package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.ui.ktx.isNotEmpty
import org.michaelbel.movies.ui.ktx.isPagingFailure
import org.michaelbel.movies.ui.ktx.isPagingLoading

@Composable
fun FeedContent(
    listState: LazyListState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding
    ) {
        items(pagingItems) { movieItem ->
            movieItem?.let { movie ->
                FeedMovieBox(
                    movie = movie,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp
                        )
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable {
                            onMovieClick(movie.movieId)
                        }
                )
            }
        }
        if (isTmdbApiKeyEmpty && pagingItems.isNotEmpty) {
            item {
                FeedApiKeyBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        FeedLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        FeedErrorBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}