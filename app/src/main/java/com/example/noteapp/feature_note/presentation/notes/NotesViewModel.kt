package com.example.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.usecase.NoteUsecase
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUsecase
) : ViewModel() {
    private val _noteState = mutableStateOf(Notestates())
    val noteStates: State<Notestates> = _noteState

    private var recentlyDeletedNote: Note? = null

    private var getNoteJob: Job? = null

    init {
        getNotes(NoteFilter.Date(OrderType.Descending))
    }

    fun onEvent(noteEvents: NoteEvents) {
        when (noteEvents) {
            is NoteEvents.Filter -> {
                if (noteStates.value.noteFilter::class == noteEvents.noteFilter::class && noteStates.value.noteFilter.orderType == noteEvents.noteFilter.orderType) {
                    return
                }
                getNotes(noteEvents.noteFilter)
            }
            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvents.DeleteNode -> {
                viewModelScope.launch {
                    noteUseCase.deleteNoteUseCase(noteEvents.note)
                    recentlyDeletedNote = noteEvents.note
                }
            }
            is NoteEvents.ToogleFilterSection -> {
                _noteState.value = noteStates.value.copy(
                    isOrderStateSectionVisible = !noteStates.value.isOrderStateSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteFilter: NoteFilter) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCase.getNoteUseCase(noteFilter)
            .onEach { notes ->
                _noteState.value = noteStates.value.copy(
                    notes = notes,
                    noteFilter = noteFilter
                )
            }.launchIn(viewModelScope)
    }
}