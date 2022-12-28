package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.usecase.NoteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel
@Inject constructor(
    private val noteUsecase: NoteUsecase
) : ViewModel() {

    private val _noteTitle: MutableState<NoteTextFieldState> = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter Title.."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent: MutableState<NoteTextFieldState> =
        mutableStateOf(
            NoteTextFieldState(
                hint = "Add Some Content..."
            )
        )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor: MutableState<Int> = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentNoteId: Int? = null

    fun onEvent(addEditNoteEvents: AddEditNoteEvents) {
        when (addEditNoteEvents) {
            is AddEditNoteEvents.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = addEditNoteEvents.text
                )
            }
            is AddEditNoteEvents.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = addEditNoteEvents.text
                )
            }
            is AddEditNoteEvents.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !addEditNoteEvents.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvents.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !addEditNoteEvents.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvents.ChangeColor -> {
                _noteColor.value = addEditNoteEvents.color
            }
            is AddEditNoteEvents.saveNote -> {
                viewModelScope.launch {
                    try {
                        noteUsecase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvents.saveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvents.showSnackbar(
                                message = e.message ?: "couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvents {
        data class showSnackbar(val message: String) : UiEvents()
        object saveNote : UiEvents()
    }
}