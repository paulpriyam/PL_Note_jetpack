package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvents {
    data class EnteredTitle(val text: String) : AddEditNoteEvents()
    data class ChangeFocusTitle(val focusState: FocusState) : AddEditNoteEvents()
    data class EnteredContent(val text: String) : AddEditNoteEvents()
    data class ChangeFocusContent(val focusState: FocusState) : AddEditNoteEvents()
    data class ChangeColor(val color: Int) : AddEditNoteEvents()
    object saveNote : AddEditNoteEvents()
}
