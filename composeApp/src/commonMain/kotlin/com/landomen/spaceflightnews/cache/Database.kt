package com.landomen.spaceflightnews.cache

import com.landomen.spaceflightnews.model.Article
import kotlinx.datetime.Instant

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQueries = database.appDatabaseQueries

    fun getNewestArticles(): List<Article> {
        return dbQueries.selectNewestArticles { id, title, url, imageUrl, newsSite, summary, publishedAt, updatedAt ->
            Article(
                id = id,
                title = title,
                url = url,
                imageUrl = imageUrl,
                newsSite = newsSite,
                summary = summary,
                publishedAt = Instant.parse(publishedAt),
                updatedAt = Instant.parse(updatedAt),
            )
        }.executeAsList()
    }

    fun getArticleById(id: Long): Article? {
        return dbQueries.selectArticleById(id) { id, title, url, imageUrl, newsSite, summary, publishedAt, updatedAt ->
            Article(
                id = id,
                title = title,
                url = url,
                imageUrl = imageUrl,
                newsSite = newsSite,
                summary = summary,
                publishedAt = Instant.parse(publishedAt),
                updatedAt = Instant.parse(updatedAt),
            )
        }.executeAsOneOrNull()
    }

    fun insertArticles(articles: List<Article>) {
        dbQueries.transaction {
            dbQueries.removeAllArticles()
            articles.forEach { article ->
                dbQueries.insertOrReplaceArticle(
                    id = article.id,
                    title = article.title,
                    url = article.url,
                    imageUrl = article.imageUrl,
                    newsSite = article.newsSite,
                    summary = article.summary,
                    publishedAt = article.publishedAt.toString(),
                    updatedAt = article.updatedAt.toString(),
                )
            }
        }
    }
}