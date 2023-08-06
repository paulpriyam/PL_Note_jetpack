package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.NotesDao
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    val dao: NotesDao
) : NoteRepository {

}