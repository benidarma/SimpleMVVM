package com.simple.mvvm.ui.screens.article.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simple.mvvm.R
import com.simple.mvvm.components.AsyncImage
import com.simple.mvvm.data.models.Article
import com.simple.mvvm.extensions.shimmer
import com.simple.mvvm.ui.theme.SimpleMVVMTheme
import com.simple.mvvm.ui.theme.ThemePreviews

@Composable
fun ArticleRow(
    article: Article,
    isLoading: Boolean,
    onNavigateToArticle: (id: Int) -> Unit = {}
) {
    Surface(
        tonalElevation = if (!isLoading) 1.dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isLoading) {
                onNavigateToArticle(article.id)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier.shimmer(isLoading = isLoading),
                data = article.urlToImage,
                contentDescription = null,
                width = 64.dp,
                height = 64.dp,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .shimmer(isLoading = isLoading),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                article.author?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = article.publishedAt,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun RowItemPreview() {
    SimpleMVVMTheme {
        Surface {
            ArticleRow(
                article = Article.dummy(),
                isLoading = false,
            )
        }
    }
}