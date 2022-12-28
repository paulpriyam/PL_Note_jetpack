package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteFilter

sealed class NoteEvents{
    class Filter(val noteFilter: NoteFilter):NoteEvents()
    class DeleteNode(val note: Note):NoteEvents()
    object RestoreNote:NoteEvents()
    object ToogleFilterSection:NoteEvents()
}
