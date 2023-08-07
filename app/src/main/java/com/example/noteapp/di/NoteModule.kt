package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NotesDao
import com.example.noteapp.feature_note.data.data_source.NotesDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(app, NotesDatabase::class.java, "Note_db").build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase): NotesDao = db.notesDao

    @Provides
    @Singleton
    fun provideNoteRepository(notesDao: NotesDao): NoteRepository {
        return NoteRepositoryImpl(notesDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(noteRepository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNoteUseCase = GetNoteUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
            getNoteByIdUseCase = GetNoteByIdUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository)
        )
    }

}