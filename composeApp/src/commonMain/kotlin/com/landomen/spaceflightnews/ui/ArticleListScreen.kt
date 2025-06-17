package com.landomen.spaceflightnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.landomen.spaceflightnews.model.Article
import kotlinx.datetime.LocalDateTime
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ArticleListScreen() {
    val viewModel = koinViewModel<ArticleListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is ArticleListViewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ArticleListViewState.Success -> {
                val articles = (state as ArticleListViewState.Success).articles
                ArticleListContent(articles = articles)
            }

            is ArticleListViewState.Error -> {
                val error = (state as ArticleListViewState.Error).message ?: "Unknown error"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = error,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = { viewModel.fetchArticles() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}


@Composable
private fun ArticleListContent(
    articles: List<Article>,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(articles) { article ->
            ArticleItem(
                article = article,
            )
        }
    }
}

@Composable
private fun ArticleItem(
    article: Article,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = article.publishedAt.toLocalDateTime(TimeZone.currentSystemDefault())
                        .format(
                            LocalDateTime.Format {
                                year()
                                char('-')
                                monthNumber()
                                char('-')
                                dayOfMonth()

                                chars(" at ")

                                hour()
                                char(':')
                                minute()
                            }),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}