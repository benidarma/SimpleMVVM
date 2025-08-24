package com.simple.mvvm.ui.screens.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.simple.mvvm.AppViewModel
import com.simple.mvvm.base.use
import com.simple.mvvm.components.EndlessLazyColumn
import com.simple.mvvm.data.models.Article
import com.simple.mvvm.ui.dialogs.ShowUIState
import com.simple.mvvm.ui.screens.article.item.ArticleRow
import com.simple.mvvm.ui.screens.empty.EmptyNewsScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    appViewModel: AppViewModel,
    viewModel: NewsViewModel = hiltViewModel(),
    onNavigateToArticle: (id: Int) -> Unit
) {
    val (state, event, error) = use(viewModel = viewModel)
    val scope = rememberCoroutineScope()

    val articles = state.articles.value
    val searchQuery by appViewModel.searchQuery.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (articles.isEmpty()) {
            event(NewsContract.Event.GetArticle(offset = 0, isReset = true))
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            scope.launch {
                delay(1500)
                isRefreshing = false
                event(NewsContract.Event.DownloadData(query = "Apple", date = "2025-08-23"))
            }
        }
    ) {
        ThisUI(
            state = state,
            searchQuery = searchQuery,
            onNavigateToArticle = onNavigateToArticle,
            onDownload = {
                event(NewsContract.Event.DownloadData(query = "Apple", date = "2025-08-23"))
            },
            onLoadMore = { offset ->
                event(NewsContract.Event.GetArticle(offset = offset, isReset = false))
            }
        )
    }

    error.ShowUIState {
        event(
            NewsContract.Event.GetArticle(
                offset = articles.size,
                isReset = articles.isEmpty()
            )
        )
    }
}

@Composable
fun ThisUI(
    state: NewsContract.State,
    searchQuery: String,
    onNavigateToArticle: (id: Int) -> Unit,
    onDownload: () -> Unit,
    onLoadMore: (offset: Int) -> Unit
) {
    val articles = state.articles.value
    val isLoading = state.articles.isLoading

    Surface {
        EndlessLazyColumn(
            modifier = Modifier.fillMaxSize(),
            isLoading = isLoading,
            searchQuery = searchQuery,
            items = articles,
            itemKey = { article -> article.id },
            filter = { article, query ->
                article.title.contains(
                    query, ignoreCase = true
                ) || article.description?.contains(
                    query, ignoreCase = true
                ) == true
            },
            loadingContent = { id ->
                ArticleRow(article = Article.dummy(id), isLoading = true)
                if (id != 8) {
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                }
            },
            itemContent = { article ->
                ArticleRow(
                    article = article,
                    isLoading = false,
                    onNavigateToArticle = onNavigateToArticle
                )
                if (article.id != articles.lastOrNull()?.id) {
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
                }
            },
            emptyContent = {
                EmptyNewsScreen(onDownload = onDownload)
            },
            loadMore = {
                onLoadMore(articles.size)
            }
        )
    }
}