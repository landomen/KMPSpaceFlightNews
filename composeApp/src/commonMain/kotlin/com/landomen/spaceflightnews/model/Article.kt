package com.landomen.spaceflightnews.model

import kotlinx.datetime.Instant

data class Article(
    val id: Long,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: Instant,
    val updatedAt: Instant,
)