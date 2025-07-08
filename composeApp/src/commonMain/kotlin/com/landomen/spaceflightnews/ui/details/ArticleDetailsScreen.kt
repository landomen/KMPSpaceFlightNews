package com.landomen.spaceflightnews.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.ui.ErrorContent
import com.landomen.spaceflightnews.ui.LoadingContent
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.back_content_description
import spaceflightnews.composeapp.generated.resources.read_more_at
import spaceflightnews.composeapp.generated.resources.share_content_description

@Composable
internal fun ArticleDetailsScreen(
    articleId: Long,
    onBackClick: () -> Unit,
) {
    val viewModel = koinViewModel<ArticleDetailsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onFetch(articleId)
    }

    ArticleDetailsScreenContent(
        state = state,
        onRetryClick = {
            viewModel.onFetch(articleId)
        },
        onBackClick = onBackClick,
        onShareClick = {
            // TODO Open share sheet
        },
        onReadMoreClick = {
            // TODO Open browser
        }
    )
}

@Composable
private fun ArticleDetailsScreenContent(
    state: ArticleDetailsViewModel.ArticleDetailsViewState,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onReadMoreClick: (String) -> Unit,
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val currentState = state) {
                is ArticleDetailsViewModel.ArticleDetailsViewState.Error -> {
                    ErrorContent(
                        errorType = currentState.errorType,
                        onRetryClick = onRetryClick,
                    )
                }

                ArticleDetailsViewModel.ArticleDetailsViewState.Loading -> {
                    LoadingContent()
                }

                is ArticleDetailsViewModel.ArticleDetailsViewState.Success -> {
                    ArticleDetailsSuccessContent(
                        article = currentState.article,
                        onBackClick = onBackClick,
                        onReadMoreClick = onReadMoreClick,
                        onShareClick = onShareClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleDetailsSuccessContent(
    article: Article,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onReadMoreClick: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            )

            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back_content_description),
                    tint = Color.White
                )
            }

            IconButton(onClick = onShareClick, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = stringResource(Res.string.share_content_description),
                    tint = Color.White
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = article.newsSite,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = " • ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
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
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            TextButton(onClick = {
                onReadMoreClick(article.url)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(Res.string.read_more_at, article.newsSite))
            }
        }
    }
}