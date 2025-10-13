package org.yusufteker.konekt.ui.screen.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.domain.models.Note
import org.yusufteker.konekt.feature.notes.NotesAction
import org.yusufteker.konekt.feature.notes.NotesState
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun NotesScreenRoot(
    viewModel: NotesViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value

    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }

    BaseContentWrapper(state = state) { modifier ->
        NotesScreen(state = state, onAction = viewModel::onAction)
    }

    LaunchedEffect(Unit) {
        viewModel.onAction(NotesAction.Init)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun NotesScreen(
    state: NotesState,
    onAction: (NotesAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(
                        NotesAction.AddNote(
                            Note(
                                id = Clock.System.now().toEpochMilliseconds().toString(),
                                title = "Yeni Not",
                                content = "İçeriği buraya yaz",
                            )
                        )
                    )
                }
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.notes.isEmpty()) {
                Text(
                    text = "Henüz not yok",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            onDelete = { onAction(NotesAction.DeleteNote(note.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(note.title, style = MaterialTheme.typography.titleMedium)
            Text(note.content, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = onDelete) { Text("Sil") }
        }
    }
}
