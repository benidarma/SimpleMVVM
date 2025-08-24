package com.simple.mvvm.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simple.mvvm.AppViewModel
import com.simple.mvvm.components.SearchTextField
import com.simple.mvvm.components.TopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    val appViewModel: AppViewModel = hiltViewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { backStackEntry?.destination?.route ?: Screen.News.value }
    }

    var isSearching by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        SearchTextField(
                            query = searchQuery,
                            onQueryChange = {
                                searchQuery = it
                                appViewModel.onChangeSearchQuery(it)
                            }
                        )
                    } else {
                        TopBarTitle(currentRoute = currentRoute)
                    }
                },
                navigationIcon = {
                    if (currentRoute == Screen.Article.value) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                actions = {
                    if (currentRoute == Screen.News.value) {
                        IconButton(onClick = {
                            isSearching = !isSearching
                            if (!isSearching) {
                                searchQuery = ""
                                appViewModel.onChangeSearchQuery("")
                            }
                        }) {
                            Icon(
                                imageVector = if (isSearching) Icons.Filled.Close else Icons.Filled.Search,
                                contentDescription = if (isSearching) "Close Search" else "Search",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.MainRoute.value,
            modifier = Modifier.padding(paddingValues)
        ) {
            navigationRootGraph(
                navController = navController,
                appViewModel = appViewModel
            )
        }
    }
}