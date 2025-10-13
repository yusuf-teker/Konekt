package org.yusufteker.konekt.feature.notes

import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.domain.models.Note

data class NotesState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val notes: List<Note> = emptyList()
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
