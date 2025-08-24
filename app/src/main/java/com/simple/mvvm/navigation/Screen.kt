package com.simple.mvvm.navigation

import androidx.annotation.StringRes
import com.simple.mvvm.R

sealed class Screen(val value: String, @StringRes val titleId: Int) {
    data object MainRoute : Screen("main-route", R.string.app_name)
    data object News : Screen("news", R.string.news)
    data object Article : Screen("article/{id}", R.string.article) {
        fun withId(id: Int) = "article/$id"
    }
}