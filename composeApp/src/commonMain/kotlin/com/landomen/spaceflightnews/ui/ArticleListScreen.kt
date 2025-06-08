package com.landomen.spaceflightnews.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.landomen.spaceflightnews.model.Article
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun ArticleListScreen() {
    val articles = remember {
        listOf(
            Article(
                id = 28392,
                title = "SpaceX launches UAE’s Thuraya-4 mobile connectivity satellite",
                url = "https://spacenews.com/spacex-launches-uaes-thuraya-4-mobile-connectivity-satellite/",
                imageUrl = "https://i0.wp.com/spacenews.com/wp-content/uploads/2025/01/Thuraya-4-scaled.jpg?fit=1024%2C635&quality=89&ssl=1",
                newsSite = "SpaceNews",
                summary = "SpaceX launched the Thuraya-4 voice and data connectivity satellite Jan. 3 for Space42, the United Arab Emirates’ recently formed AI-powered space technology champion.\r\nThe post SpaceX launches UAE’s Thuraya-4 mobile connectivity satellite appeared first on SpaceNews.",
                publishedAt = Instant.parse("2025-01-04T02:04:26Z"),
                updatedAt = Instant.parse("2025-01-04T08:35:00.677975Z"),
            ),
            Article(
                id = 28391,
                title = "NASA sees strong support for strategy to maintain continuous human presence in LEO",
                url = "https://spacenews.com/nasa-sees-strong-support-for-strategy-to-maintain-continuous-human-presence-in-leo/",
                imageUrl = "https://i0.wp.com/spacenews.com/wp-content/uploads/2024/12/54209298773_f912740933_k.jpg?fit=1024%2C683&quality=89&ssl=1",
                newsSite = "SpaceNews",
                summary = "NASA’s deputy administrator says there is nearly unanimous support for its LEO microgravity strategy that endorses keeping humans in orbit continuously.\nThe post NASA sees strong support for strategy to maintain continuous human presence in LEO appeared first on SpaceNews.",
                publishedAt = Instant.parse("2025-01-03T23:54:59Z"),
                updatedAt = Instant.parse("2025-01-04T00:00:25.390033Z"),
            ),
            Article(
                id = 28390,
                title = "SpaceX achieves record-breaking 2024, looks ahead to 2025",
                url = "https://www.nasaspaceflight.com/2025/01/spacex-roundup-2024/",
                imageUrl = "https://www.nasaspaceflight.com/wp-content/uploads/2025/01/SpaceX-in-2024-collage.png",
                newsSite = "NASASpaceflight",
                summary = "SpaceX is gearing up for another record-breaking year in 2025 after surpassing all previous record-breaking…\nThe post SpaceX achieves record-breaking 2024, looks ahead to 2025 appeared first on NASASpaceFlight.com.",
                publishedAt = Instant.parse("2025-01-03T22:24:00Z"),
                updatedAt = Instant.parse("2025-01-03T23:20:12.779937Z"),
            ),
        )
    }

    ArticleListContent(
        articles = articles,
    )
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