package org.yusufteker.konekt.feature.notes

import org.yusufteker.konekt.domain.models.Note

sealed interface NotesAction {
    object Init : NotesAction
    object NavigateBack : NotesAction

    data class AddNote(val note: Note) : NotesAction
    data class DeleteNote(val id: String) : NotesAction
    data class UpdateNote(val note: Note) : NotesAction
}
