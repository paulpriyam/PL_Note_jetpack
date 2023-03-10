package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType

data class Notestates(
    val noteFilter: NoteFilter = NoteFilter.Date(OrderType.Descending),
    val notes: List<Note> = emptyList(),
    val isOrderStateSectionVisible: Boolean = false
)
