package org.yusufteker.konekt.data.repository


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.domain.models.Note
import org.yusufteker.konekt.domain.repository.NoteRepository

class NoteRepositoryImpl : NoteRepository {

    private val _notes = MutableStateFlow(
        listOf(
            Note("1", "İlk Not", "Bu bir test notudur."),
            Note("2", "Alışveriş Listesi", "Ekmek, Süt, Yumurta"),
            Note("3", "Proje Planı", "Konekt modüllerini organize et")
        )
    )

    override fun getNotes(): StateFlow<List<Note>> = _notes.asStateFlow()


    override suspend fun addNote(note: Note) {
        _notes.update { it + note }
    }

    override suspend fun updateNote(note: Note) {
        _notes.update { current -> current.map { if (it.id == note.id) note else it } }
    }

    override suspend fun deleteNote(id: String) {
        _notes.update { it.filterNot { it.id == id } }
    }
}
