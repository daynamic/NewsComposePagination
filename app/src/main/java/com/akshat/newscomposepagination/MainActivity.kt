package com.akshat.newscomposepagination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.akshat.newscomposepagination.navigation.NewsPaginationNavigaton
import com.akshat.newscomposepagination.ui.theme.NewsComposePaginationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsComposePagination()
        }
    }
}

@Composable
fun NewsComposePagination() {
    NewsComposePaginationTheme {
        Scaffold(containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()) { _ ->
            NewsPaginationNavigaton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsComposePagination()
}