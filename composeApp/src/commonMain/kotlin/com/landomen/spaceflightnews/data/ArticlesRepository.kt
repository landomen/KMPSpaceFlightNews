package com.landomen.spaceflightnews.data

import com.landomen.spaceflightnews.cache.Database
import com.landomen.spaceflightnews.cache.DatabaseDriverFactory
import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.network.ApiService

internal class ArticlesRepository(
    databaseDriverFactory: DatabaseDriverFactory,
    private val api: ApiService
) {
    private val database = Database(databaseDriverFactory)

    suspend fun getArticles(): List<Article> {
        try {
            return api.getArticles().also {
                database.insertArticles(it)
            }
        } catch (e: Exception) {
            try {
                val storedArticles = database.getNewestArticles()
                if (storedArticles.isNotEmpty()) {
                    return storedArticles
                }
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun getArticleById(id: Long): Article? {
        return database.getArticleById(id)
    }
}