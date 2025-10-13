package org.yusufteker.konekt.ui.screen.notes.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.yusufteker.konekt.domain.models.Note
import org.yusufteker.konekt.domain.repository.NoteRepository
import org.yusufteker.konekt.feature.notes.NotesAction
import org.yusufteker.konekt.feature.notes.NotesState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel

class NotesViewModel(
    private val noteRepository: NoteRepository
) :
    PlatformBaseViewModel<NotesState>(NotesState()) {

    fun onAction(action: NotesAction) {
        when (action) {
            is NotesAction.Init -> loadNotes()
            is NotesAction.AddNote -> addNote(action.note)
            is NotesAction.DeleteNote -> deleteNote(action.id)
            is NotesAction.UpdateNote -> updateNote(action.note)
            NotesAction.NavigateBack -> TODO()
        }
    }


    private fun loadNotes() {
        noteRepository.getNotes().onEach { notes ->
            setState {
                copy(notes = notes)
            }
        }.launchIn(viewModelScope)
    }

    private fun addNote(note: Note) = launchWithLoading {
        noteRepository.addNote(note)
    }

    private fun deleteNote(id: String) = launchWithLoading {
        noteRepository.deleteNote(id)
    }

    private fun updateNote(note: Note) = launchWithLoading {
        noteRepository.updateNote(note)
    }

    private fun refreshNotes() = launchWithLoading {
        setState { copy(notes = state.value.notes.shuffled()) }
    }
}
