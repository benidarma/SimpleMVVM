package com.simple.mvvm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    data: Any?,
    width: Dp? = null,
    height: Dp,
    placeholder: Painter? = null,
    error: Painter? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    roundedSize: Dp = 2.dp
) {

    val newWidth = width ?: LocalConfiguration.current.screenWidthDp.dp
    var isSuccess by remember { mutableStateOf(false) }

    coil.compose.AsyncImage(
        model = data,
        contentDescription = contentDescription,
        modifier = modifier.then(
            Modifier
                .width(newWidth)
                .height(height)
                .clip(RoundedCornerShape(roundedSize))
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(roundedSize)
                )
        ),
        placeholder = placeholder,
        error = error,
        onSuccess = { isSuccess = true },
        contentScale = if (isSuccess) contentScale else ContentScale.Inside
    )
}