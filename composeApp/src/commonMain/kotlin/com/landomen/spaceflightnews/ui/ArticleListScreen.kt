package com.landomen.spaceflightnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.app_name
import spaceflightnews.composeapp.generated.resources.no_internet
import spaceflightnews.composeapp.generated.resources.retry
import spaceflightnews.composeapp.generated.resources.server_error
import spaceflightnews.composeapp.generated.resources.something_went_wrong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticleListScreen(onArticleClick: (Long) -> Unit) {
    val viewModel = koinViewModel<ArticleListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.app_name))
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (val currentState = state) {
                is ArticleListViewState.Loading -> {
                    LoadingContent()
                }

                is ArticleListViewState.Success -> {
                    val articles = currentState.articles
                    ArticleListContent(
                        articles = articles,
                        onArticleClick = onArticleClick,
                    )
                }

                is ArticleListViewState.Error -> {
                    ErrorContent(
                        errorType = currentState.errorType,
                        onRetryClick = { viewModel.fetchArticles() }
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.LoadingContent() {
    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
}

@Composable
fun BoxScope.ErrorContent(
    errorType: ErrorType,
    onRetryClick: () -> Unit
) {
    val errorMessage = when (errorType) {
        ErrorType.NoInternet -> stringResource(Res.string.no_internet)
        ErrorType.ServerError -> stringResource(Res.string.server_error)
        ErrorType.Unknown -> stringResource(Res.string.something_went_wrong)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(Alignment.Center)
    ) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onRetryClick) {
            Text(text = stringResource(Res.string.retry))
        }
    }
}

@Composable
private fun ArticleListContent(
    articles: List<Article>,
    onArticleClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(articles) { article ->
            ArticleItem(
                article = article,
                onArticleClick = onArticleClick,
            )
        }
    }
}

@Composable
private fun ArticleItem(
    article: Article,
    onArticleClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onArticleClick(article.id) }
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