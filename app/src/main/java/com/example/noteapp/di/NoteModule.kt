package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_note.data.data_source.NotesDatabase
import com.example.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.usecase.AddNoteUseCase
import com.example.noteapp.feature_note.domain.usecase.DeleteNoteUseCase
import com.example.noteapp.feature_note.domain.usecase.GetNoteUseCase
import com.example.noteapp.feature_note.domain.usecase.NoteUsecase
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
    fun providesNoteDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(app, NotesDatabase::class.java, "Note_db").build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun providesNotesUsecase(repository: NoteRepository): NoteUsecase {
        return NoteUsecase(
            getNoteUseCase = GetNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository)
        )
    }
}