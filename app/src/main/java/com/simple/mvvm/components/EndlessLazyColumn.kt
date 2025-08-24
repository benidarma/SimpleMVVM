package com.simple.mvvm.components

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simple.mvvm.extensions.reachedBottom

@Composable
fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    isLoading: Boolean,
    searchQuery: String = "",
    items: List<T>,
    itemKey: (T) -> Any,
    loadingContent: @Composable (Int) -> Unit,
    itemContent: @Composable (T) -> Unit,
    emptyContent: @Composable () -> Unit,
    loadMore: () -> Unit,
    filter: (T, String) -> Boolean = { _, _ -> true }
) {
    val reachedBottom by remember {
        derivedStateOf { state.reachedBottom() }
    }

    val filteredItems = remember(searchQuery, items) {
        if (searchQuery.isBlank()) items else items.filter { filter(it, searchQuery) }
    }

    LaunchedEffect(reachedBottom, isLoading, items.size) {
        if (reachedBottom && !isLoading && items.isNotEmpty()) {
            loadMore()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
    ) {
        when {
            isLoading && items.isEmpty() -> {
                items(8) { id -> loadingContent(id) }
            }

            filteredItems.isEmpty() -> {
                item { emptyContent() }
            }

            else -> {
                items(
                    count = filteredItems.size,
                    key = { index -> itemKey(filteredItems[index]) }
                ) { index ->
                    itemContent(filteredItems[index])
                }

                if (isLoading) {
                    item {
                        loadingContent(filteredItems.size)
                    }
                }
            }
        }
    }
}