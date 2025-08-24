package com.simple.mvvm.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.simple.mvvm.navigation.Screen

@Composable
fun TopBarTitle(currentRoute: String) {
    val titleId = when (currentRoute) {
        Screen.News.value -> Screen.News.titleId
        Screen.Article.value -> Screen.Article.titleId
        else -> Screen.MainRoute.titleId
    }

    Text(
        text = stringResource(titleId),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}
