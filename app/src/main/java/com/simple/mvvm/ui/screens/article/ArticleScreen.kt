package com.simple.mvvm.ui.screens.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simple.mvvm.R
import com.simple.mvvm.base.use
import com.simple.mvvm.components.AsyncImage
import com.simple.mvvm.extensions.shimmer
import com.simple.mvvm.ui.dialogs.ShowUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    viewModel: ArticleViewModel = hiltViewModel(),
    id: Int
) {
    val (state, event, error) = use(viewModel = viewModel)
    val scope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        event(
            ArticleContract.Event.GetArticle(id = id)
        )
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            scope.launch {
                delay(1500)
                isRefreshing = false
                event(
                    ArticleContract.Event.GetArticle(id = id)
                )
            }
        }
    ) {
        ThisUI(
            state = state,
        )
    }

    error.ShowUIState {

    }
}

@Composable
fun ThisUI(
    state: ArticleContract.State
) {
    val article = state.article.value
    val isLoading = state.article.isLoading

    Surface {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "${article?.title}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "by ${article?.author} • ${article?.publishedAt}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                AsyncImage(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    data = article?.urlToImage,
                    contentDescription = null,
                    height = 200.dp,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                )

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "${article?.description}",
                    style = MaterialTheme.typography.bodyMedium
                )

                HorizontalDivider(modifier = Modifier.shimmer(isLoading = isLoading))

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "Content",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "${article?.content}",
                    style = MaterialTheme.typography.bodySmall
                )

                HorizontalDivider(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                )

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "Source: ${article?.source?.name}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    modifier = Modifier.shimmer(isLoading = isLoading),
                    text = "URL: ${article?.url}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}