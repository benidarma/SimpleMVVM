package com.simple.mvvm.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.simple.mvvm.data.network.ApiError
import com.simple.mvvm.extensions.CollectInLaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun Flow<ApiError>.ShowUIState(onRetry: (apiCode: String?) -> Unit) {
    var error by remember {
        mutableStateOf(
            ApiError(99, null)
        )
    }
    var showDialog by remember { mutableStateOf(false) }

    CollectInLaunchedEffect {
        error = it
        showDialog = true
    }

    if (showDialog) AlertDialog(
        onDismissRequest = { showDialog = false },
        title = { Text("Oops, something went wrong") },
        text = {
            Text(
                text = error.apiCode ?: "Unknown error",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog = false
                    onRetry(error.apiCode)
                }
            ) {
                Text("Retry", modifier = Modifier)
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog = false }) {
                Text("Cancel")
            }
        }
    )
}