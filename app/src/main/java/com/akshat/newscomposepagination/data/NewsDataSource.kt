package com.akshat.newscomposepagination.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akshat.newscomposepagination.model.Article
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val repository: NewsRepository,
    private val query: String
) : PagingSource<Long, Article>() {

    /**
     * Returns the key for the next page to be loaded when refreshing.
     *
     * This method is used by the Paging library to determine the starting point
     * for loading data when the user performs a refresh action (e.g., swipe-to-refresh).
     *
     * @param state The current state of the Paging system.
     * @return The page key to refresh from or null if no valid refresh key exists.
     */

    override fun getRefreshKey(state: PagingState<Long, Article>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Loads a specific page of data based on the given key.
     *
     * The Paging library calls this method when it needs to load more data.
     * This implementation fetches data from the repository using the `query`
     * and the `key` (current page number).
     *
     * @param params Contains information about the requested load size and key.
     * @return A LoadResult object containing either the data or an error.
     */

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Article> {
        return try {
            val nextPage = params.key ?: 1L
            val newsResponse = repository.fetchNews(query = query, nextPage = nextPage)

            if(newsResponse.articles.isNotEmpty()){
                LoadResult.Page(
                    data = newsResponse.articles,
                    prevKey = if (nextPage == 1L) null else nextPage - 1,
                    nextKey = nextPage.plus(1)
                )
            } else {
                LoadResult.Error(Exception("No results found"))
            }
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}