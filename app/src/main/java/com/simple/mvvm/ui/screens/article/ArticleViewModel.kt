package com.simple.mvvm.ui.screens.article

import androidx.lifecycle.viewModelScope
import com.simple.mvvm.base.AppViewModel
import com.simple.mvvm.base.Data
import com.simple.mvvm.data.local.ArticleRepository
import com.simple.mvvm.data.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : AppViewModel<ArticleContract.State, ArticleContract.Event>() {
    override fun createInitialState() = ArticleContract.State(
        article = Data(isLoading = true, value = Article.dummy(1))
    )

    override fun event(event: ArticleContract.Event) {
        when (event) {
            is ArticleContract.Event.GetArticle -> getArticle(
                id = event.id
            )
        }
    }

    private fun getArticle(
        id: Int
    ) = viewModelScope.launch {
        updateState {
            copy(article = article.copy(isLoading = true))
        }

        articleRepository.getById(
            id = id
        ).collectLatest {
            delay(4000)

            updateState {
                copy(
                    article = article.copy(
                        isLoading = false,
                        value = it
                    )
                )
            }
        }
    }
}