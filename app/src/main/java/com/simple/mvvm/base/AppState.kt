package com.simple.mvvm.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simple.mvvm.data.network.ApiError
import kotlinx.coroutines.flow.Flow

interface ViewState
interface ViewEvent

data class Data<T>(
    val isLoading: Boolean,
    val value: T,
)

data class StateDispatch<State : ViewState, Event : ViewEvent>(
    val state: State,
    val event: (Event) -> Unit,
    val error: Flow<ApiError>,
)

@Composable
inline fun <reified State : ViewState, Event : ViewEvent> use(
    viewModel: AppViewModel<State, Event>
): StateDispatch<State, Event> {

    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val event: (Event) -> Unit = { viewModel.event(it) }

    return StateDispatch(
        state = state,
        event = event,
        error = viewModel.error,
    )
}