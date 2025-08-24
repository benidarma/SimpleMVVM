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
import com.simple.mvvm.ui.screens.movie.MoviesScreen
import com.simple.mvvm.ui.screens.movie.details.MovieDetailsScreen

fun NavGraphBuilder.navigationRootGraph(
    navController: NavHostController,
) {
    navigation(
        route = Screen.MainRoute.value,
        startDestination = Screen.Movies.value
    ) {
        composable(Screen.Movies.value) {
            MoviesScreen(
                onMovieDetails = {
                    navController.navigate(
                        Screen.MovieDetails.withId(it)
                    )
                }
            )
        }
        composable(
            route = Screen.MovieDetails.value,
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
            MovieDetailsScreen(
                id = entry.arguments?.getInt("id") ?: 0
            )
        }
    }
}