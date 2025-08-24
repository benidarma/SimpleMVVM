package com.simple.mvvm.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.simple.mvvm.AppViewModel
import com.simple.mvvm.ui.screens.article.ArticleScreen
import com.simple.mvvm.ui.screens.news.NewsScreen

fun NavGraphBuilder.navigationRootGraph(
    navController: NavHostController,
    appViewModel: AppViewModel
) {
    navigation(
        route = Screen.MainRoute.value,
        startDestination = Screen.News.value
    ) {
        composable(Screen.News.value) {
            NewsScreen(
                appViewModel = appViewModel,
                onNavigateToArticle = {
                    navController.navigate(
                        Screen.Article.withId(it)
                    )
                }
            )
        }
        composable(
            route = Screen.Article.value,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(600)
                )
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(600)
                )
            }
        ) { entry ->
            ArticleScreen(
                id = entry.arguments?.getInt("id") ?: 0
            )
        }
    }
}