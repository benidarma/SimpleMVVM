package com.simple.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simple.mvvm.navigation.NavigationRoot
import com.simple.mvvm.ui.theme.SimpleMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleMVVMTheme(
                dynamicColor = false
            ) {
                NavigationRoot()
            }
        }
    }
}