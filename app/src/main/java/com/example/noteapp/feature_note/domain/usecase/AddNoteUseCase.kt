package com.example.noteapp.feature_note.domain.usecase

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AddNoteUseCase(private val repository: NoteRepository) {

    operator fun invoke(
        noteFilter: NoteFilter = NoteFilter.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteFilter.orderType) {
                OrderType.Ascending -> {
                    when (noteFilter) {
                        is NoteFilter.Date -> notes.sortedBy { it.timeStamp }
                        is NoteFilter.Title -> notes.sortedBy { it.title }
                        is NoteFilter.Color -> notes.sortedBy { it.color }
                    }
                }
                OrderType.Descending -> {
                    when (noteFilter) {
                        is NoteFilter.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteFilter.Title -> notes.sortedByDescending { it.title }
                        is NoteFilter.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}