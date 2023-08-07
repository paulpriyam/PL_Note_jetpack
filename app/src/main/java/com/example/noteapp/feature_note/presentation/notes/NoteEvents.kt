package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteFilter

sealed class NoteEvents {
    data class Filter(val noteFilter: NoteFilter) : NoteEvents()
    data class DeleteNote(val note: Note) : NoteEvents()

    object RestoreNote : NoteEvents()

    object ToggleFilterSection : NoteEvents()
}
