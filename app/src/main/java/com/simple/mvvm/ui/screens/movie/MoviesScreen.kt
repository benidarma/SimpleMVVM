package com.simple.mvvm.ui.screens.movie

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.simple.mvvm.base.use

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieDetails: (id: Int) -> Unit
) {
    val (state, event, error) = use(viewModel = viewModel)
}