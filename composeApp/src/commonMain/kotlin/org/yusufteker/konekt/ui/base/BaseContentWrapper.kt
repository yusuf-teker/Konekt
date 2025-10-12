package org.yusufteker.konekt.ui.base

import org.yusufteker.konekt.base.BaseState



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun <T : BaseState> BaseContentWrapper(
    state: T,
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit = { DefaultLoading() },
    content: @Composable (Modifier) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content(modifier.fillMaxSize())

        if (state.isLoading) {
            Box(
                modifier = Modifier.matchParentSize()
                    .pointerInput(Unit) {
                        awaitPointerEventScope { while (true) awaitPointerEvent() }
                    }
                    .background(Color.Transparent)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                loadingContent()
            }
        }
    }
}

@Composable
fun DefaultLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
