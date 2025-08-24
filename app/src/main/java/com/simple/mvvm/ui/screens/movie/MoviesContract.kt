package com.simple.mvvm.ui.screens.movie

import com.simple.mvvm.base.Data
import com.simple.mvvm.base.ViewEvent
import com.simple.mvvm.base.ViewState
import com.simple.mvvm.data.models.Movie

class MoviesContract {

    data class State(
        val movies: Data<Movie>,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object GetMovies : Event()
    }
}