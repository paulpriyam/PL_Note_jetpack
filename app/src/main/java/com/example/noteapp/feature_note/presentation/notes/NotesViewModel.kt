package com.example.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.usecase.NoteUseCase
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NotesViewModel(
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    init {
        getNotes(NoteFilter.Date(OrderType.Descending))
    }

    private var _state: MutableState<NoteStates> = mutableStateOf(NoteStates())
    val state: State<NoteStates> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNoteJob: Job? = null
    fun onEvent(event: NoteEvents) {
        when (event) {
            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvents.Filter -> {
                if (state.value.noteFilter::class == event.noteFilter::class && state.value.noteFilter.orderType == event.noteFilter.orderType) {
                    return
                }
                getNotes(event.noteFilter)
            }
            is NoteEvents.ToggleFilterSection -> {
                _state.value = state.value.copy(
                    isOrderStateSectionVisible = !state.value.isOrderStateSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteFilter: NoteFilter) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCase.getNoteUseCase().onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteFilter = noteFilter
            )
        }.launchIn(viewModelScope)
    }
}