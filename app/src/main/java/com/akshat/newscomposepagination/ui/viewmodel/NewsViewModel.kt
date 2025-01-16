package com.akshat.newscomposepagination.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.akshat.newscomposepagination.data.NewsRepository
import com.akshat.newscomposepagination.data.NewsRepository.Companion.PREFETCH_DISTANCE
import com.akshat.newscomposepagination.data.NewsRepository.Companion.PAGE_SIZE
import com.akshat.newscomposepagination.data.NewsDataSource
import com.akshat.newscomposepagination.ui.components.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> = mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _inputText: MutableStateFlow<String> = MutableStateFlow("Android")
    val inputText: MutableStateFlow<String> = _inputText

    val news = inputText
        .filter { it.isNotEmpty() }
        .debounce(300.milliseconds) // wait 300ms after the last input before processing to reduce API calls.
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE,
                    initialLoadSize = PAGE_SIZE,
                ),
                pagingSourceFactory = {
                    NewsDataSource(newsRepository, query)
                }

            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    // some articles come with removed content so adding this line
                    // to remove those articles from the list
                    pagingData.filter { it.title != "[Removed]" }
                }

        }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchInput(newValue: String) {
        _inputText.value = newValue
    }

    fun onSearchClosed() {
        updateSearchInput("movies")
        updateSearchWidgetState(SearchWidgetState.CLOSED)
    }

}