package com.simple.mvvm.navigation

import androidx.annotation.StringRes
import com.simple.mvvm.R

sealed class Screen(val value: String, @StringRes val titleId: Int) {
    data object MainRoute : Screen("main-route", R.string.app_name)
    data object Movies : Screen("movies", R.string.movies)
    data object MovieDetails : Screen("movie/{id}", R.string.movie_details) {
        fun withId(id: Int) = "movie/$id"
    }
}