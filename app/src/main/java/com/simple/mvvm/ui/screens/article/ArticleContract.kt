package com.simple.mvvm.ui.screens.article

import com.simple.mvvm.base.Data
import com.simple.mvvm.base.ViewEvent
import com.simple.mvvm.base.ViewState
import com.simple.mvvm.data.models.Article

class ArticleContract {

    data class State(
        val article: Data<Article?>
    ) : ViewState

    sealed class Event : ViewEvent {
        data class GetArticle(
            val id: Int
        ) : Event()
    }
}