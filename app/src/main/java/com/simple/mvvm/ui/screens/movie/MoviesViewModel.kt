package com.simple.mvvm.ui.screens.movie

import com.simple.mvvm.base.AppViewModel
import com.simple.mvvm.data.local.LocalRepository
import com.simple.mvvm.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val localRepository: LocalRepository
) : AppViewModel<MoviesContract.State, MoviesContract.Event>() {
    override fun createInitialState(): MoviesContract.State {
        TODO("Not yet implemented")
    }

    override fun event(event: MoviesContract.Event) {
        TODO("Not yet implemented")
    }
}