package com.akshat.newscomposepagination.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.akshat.newscomposepagination.ui.components.BookPager
import com.akshat.newscomposepagination.ui.components.BookPagerOrientation
import com.akshat.newscomposepagination.ui.components.ErrorScreen
import com.akshat.newscomposepagination.ui.components.LoadingItem
import com.akshat.newscomposepagination.ui.components.NewsItemFlipBoardStyle
import com.akshat.newscomposepagination.ui.components.SearchMenu
import com.akshat.newscomposepagination.ui.components.SearchWidgetState
import com.akshat.newscomposepagination.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onItemClicked: (url: String) -> Unit,
    onShareButtonClicked: (url: String) -> Unit
) {
    val news = viewModel.news.collectAsLazyPagingItems()
    val inputText = viewModel.inputText.collectAsState()
    val searchWidgetState by viewModel.searchWidgetState


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val loadState = news.loadState
        when (loadState.refresh) {
            is LoadState.Loading -> {
                LoadingItem()
            }
            is LoadState.Error -> {
                val error = (loadState.refresh as LoadState.Error).error
                ErrorScreen(errorMessage = error.message ?: error.toString()) {
                    news.refresh()
                }
            }
            else -> {
                // News List
                val pagerState = rememberPagerState { news.itemCount }
                BookPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    orientation = BookPagerOrientation.Vertical,
                ) { page ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
                    ) {
                        news[page]?.let {
                            NewsItemFlipBoardStyle(
                                modifier = Modifier.align(Alignment.Center),
                                article = it,
                                onItemClicked = onItemClicked,
                                onShareButtonClicked = onShareButtonClicked
                            )
                        }
                    }
                }

                // Search menu
                SearchMenu(
                    inputText = inputText.value,
                    searchWidgetState = searchWidgetState,
                    onTextChange = { viewModel.updateSearchInput(it) },
                    onCloseClicked = {
                        viewModel.onSearchClosed()
                        news.refresh()
                    },
                    onSearchClicked = { news.refresh() },
                    onSearchIconClicked = {
                        viewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                    }
                )
            }
        }
    }
}