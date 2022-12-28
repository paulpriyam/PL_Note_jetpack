package com.example.noteapp.feature_note.domain.usecase

data class NoteUsecase(
    val getNoteUseCase: GetNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase
)
