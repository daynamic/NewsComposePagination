package com.akshat.newscomposepagination.data

import com.akshat.newscomposepagination.BuildConfig
import com.akshat.newscomposepagination.model.NewsApiResponse
import com.akshat.newscomposepagination.network.NewsApiService
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsApiService: NewsApiService) {

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 10
    }

    suspend fun fetchNews(
        query: String,
        nextPage: Long
    ): NewsApiResponse{
        return try {
            newsApiService.fetchFeed(
                q = query,
                apiKey = BuildConfig.api_key,
                pageSize = PAGE_SIZE,
                page = nextPage
            )
        } catch (e: Exception){
            e.printStackTrace()
            throw Exception(e)
        }
    }

}