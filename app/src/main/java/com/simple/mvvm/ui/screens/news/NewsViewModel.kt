package com.simple.mvvm.ui.screens.news

import androidx.lifecycle.viewModelScope
import com.simple.mvvm.BuildConfig
import com.simple.mvvm.base.AppViewModel
import com.simple.mvvm.base.Data
import com.simple.mvvm.data.local.ArticleRepository
import com.simple.mvvm.data.models.Article
import com.simple.mvvm.data.models.News
import com.simple.mvvm.data.network.ApiRepository
import com.simple.mvvm.data.network.onError
import com.simple.mvvm.data.network.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val articleRepository: ArticleRepository
) : AppViewModel<NewsContract.State, NewsContract.Event>() {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val PAGE_LIMIT = 10
    }

    override fun createInitialState() = NewsContract.State(
        news = Data(isLoading = true, value = null),
        articles = Data(isLoading = true, value = emptyList())
    )

    override fun event(event: NewsContract.Event) {
        when (event) {
            is NewsContract.Event.DownloadData -> downloadData(
                event.query, event.date, event.sortBy
            )

            is NewsContract.Event.GetArticle -> getArticle(
                offset = event.offset, isReset = event.isReset
            )
        }
    }

    private fun downloadData(
        query: String,
        date: String,
        sortBy: String
    ) = viewModelScope.launch {
        apiRepository.getNews(
            query = query, from = date, sortBy = sortBy, apiKey = API_KEY
        )
            .onStart {
                updateState {
                    copy(
                        articles = articles.copy(isLoading = true, value = emptyList())
                    )
                }
            }
            .onError { updateError(it) }
            .onSuccess {
                saveArticle(it)
            }
    }

    private fun getArticle(
        offset: Int, isReset: Boolean
    ) = viewModelScope.launch {
        updateState {
            copy(articles = articles.copy(isLoading = true))
        }

        articleRepository.getPaginated(
            limit = PAGE_LIMIT, offset = offset
        ).collectLatest {
            delay(4500)

            val listArticle = if (isReset) it else {
                val list = mutableListOf<Article>()
                val articles = viewState.value.articles.value
                list.addAll(articles)
                it.forEach { item ->
                    if (articles.find { i -> i.title == item.title } == null) {
                        list.add(item)
                    }
                }
                list
            }

            updateState {
                copy(articles = articles.copy(isLoading = false, value = listArticle))
            }
        }
    }

    private fun saveArticle(news: News) = viewModelScope.launch {
        news.articles.forEach { article ->
            articleRepository.save(article)
        }

        event(NewsContract.Event.GetArticle(0, true))
    }
}