package org.yusufteker.konekt.ui.screen.tasklist.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.yusufteker.konekt.ui.base.BaseContentWrapper

@Composable
fun TaskListScreenRoot(
    viewModel: TaskListViewModel,
) {
    val state = viewModel.state.collectAsState().value

    BaseContentWrapper(state = state) { modifier ->
        TaskListScreen(state = state, onAction = viewModel::onAction)
    }
}

@Composable
fun TaskListScreen(
    state: TaskListState,
    onAction: (TaskListAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Task List Screen (boş)")
    }
}
