package com.example.noteapp.feature_note.domain.usecase

data class NoteUseCase(
    val getNoteUseCase: GetNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)
