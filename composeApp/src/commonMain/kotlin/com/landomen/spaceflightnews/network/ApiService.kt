package com.landomen.spaceflightnews.network

import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.network.model.ArticlesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiService {

    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getArticles(): List<Article> {
        return httpClient.get("${BASE_URL}/articles/?format=json")
            .body<ArticlesResponse>().results.map {
                val imageUrl = it.imageUrl.ensureHttpsUrl()
                it.copy(imageUrl = imageUrl, summary = it.summary.trim(), title = it.title.trim())
            }
    }

    private fun String.ensureHttpsUrl(): String {
        return if (startsWith("https")) {
            this
        } else {
            replaceFirst("http", "https")
        }
    }

    companion object {
        private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4"
    }
}