package com.example.noteapp.feature_note.domain.usecase

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNoteUseCase(val repository: NoteRepository) {

    operator fun invoke(noteFilter: NoteFilter): Flow<List<Note>> {
        return repository.getAllNotes().map { notes ->
            when (noteFilter.orderType) {
                OrderType.Ascending -> {
                    when (noteFilter) {
                        is NoteFilter.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteFilter.Date -> notes.sortedBy { it.timeStamp }
                        is NoteFilter.Color -> notes.sortedBy { it.color }
                    }
                }
                OrderType.Descending -> {
                    when (noteFilter) {
                        is NoteFilter.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteFilter.Date -> notes.sortedBy { it.timeStamp }
                        is NoteFilter.Color -> notes.sortedBy { it.color }
                    }
                }
            }
        }
    }
}