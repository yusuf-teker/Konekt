package org.yusufteker.konekt.domain.repository

import kotlinx.coroutines.flow.StateFlow
import org.yusufteker.konekt.domain.models.Note

interface NoteRepository {
    fun getNotes(): StateFlow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: String)
}