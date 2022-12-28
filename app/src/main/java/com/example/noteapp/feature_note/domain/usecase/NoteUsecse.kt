package com.example.noteapp.feature_note.domain.usecase

data class NoteUsecse(
    val getNoteUseCase: GetNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
)
