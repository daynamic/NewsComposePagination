package com.akshat.newscomposepagination.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akshat.newscomposepagination.ui.screen.HomeScreen
import com.akshat.newscomposepagination.ui.viewmodel.NewsViewModel
import com.akshat.newscomposepagination.util.openUrl
import com.akshat.newscomposepagination.util.share

@Composable
fun NewsPaginationNavigaton(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "newsPosts"
    ) {

        composable(route = "newsPosts") {
            val newsViewModel = hiltViewModel<NewsViewModel>()
            val context = LocalContext.current
            HomeScreen(
                viewModel = newsViewModel,
                onItemClicked = { context.openUrl(it.toString()) },
                onShareButtonClicked = { context.share(it.toString()) }
            )
        }


    }
}