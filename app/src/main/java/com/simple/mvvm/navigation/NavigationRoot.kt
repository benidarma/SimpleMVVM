package com.simple.mvvm.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {},
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.MainRoute.value,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            navigationRootGraph(navController)
        }
    }
}