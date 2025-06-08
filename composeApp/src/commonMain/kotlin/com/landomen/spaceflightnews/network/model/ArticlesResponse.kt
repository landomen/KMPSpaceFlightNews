package com.landomen.spaceflightnews.network.model

import com.landomen.spaceflightnews.model.Article
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    val results: List<Article>
)