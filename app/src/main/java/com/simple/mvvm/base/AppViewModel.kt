package com.simple.mvvm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.mvvm.data.network.ApiError
import com.simple.mvvm.extensions.readOnly
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class AppViewModel<State : ViewState, Event : ViewEvent> : ViewModel() {

    private val initialState: State by lazy {
        createInitialState()
    }

    private val _viewState = MutableStateFlow(
        initialState
    )
    val viewState = _viewState.readOnly()

    private val _error = Channel<ApiError>()
    val error = _error.receiveAsFlow()

    abstract fun createInitialState(): State

    abstract fun event(event: Event)

    protected fun updateState(
        reduce: State.() -> State
    ) = viewModelScope.launch {
        _viewState.update { viewState.value.reduce() }
    }

    protected fun updateError(
        error: ApiError?
    ) = viewModelScope.launch {
        if (error == null) return@launch

        _error.send(error)
    }
}