package com.akshat.newscomposepagination.model

data class NewsApiResponse(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)
