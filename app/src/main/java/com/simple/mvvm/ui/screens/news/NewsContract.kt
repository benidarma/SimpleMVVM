package com.simple.mvvm.ui.screens.news

import com.simple.mvvm.base.Data
import com.simple.mvvm.base.ViewEvent
import com.simple.mvvm.base.ViewState
import com.simple.mvvm.data.models.Article
import com.simple.mvvm.data.models.News

class NewsContract {

    data class State(
        val news: Data<News?>,
        val articles: Data<List<Article>>
    ) : ViewState

    sealed class Event : ViewEvent {
        data class DownloadData(
            val query: String,
            val date: String,
            val sortBy: String = "popularity"
        ) : Event()

        data class GetArticle(
            val offset: Int, val isReset: Boolean
        ) : Event()
    }
}