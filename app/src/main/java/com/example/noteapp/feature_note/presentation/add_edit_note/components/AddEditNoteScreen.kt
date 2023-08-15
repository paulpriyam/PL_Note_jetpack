package com.example.noteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presentation.add_edit_note.AddEditNoteEvents
import com.example.noteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()
    val noteBgAnimatable = remember {
        Animatable(Color(if (noteColor != -1) noteColor else viewModel.noteColor.value))
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvents.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditNoteViewModel.UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvents.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBgAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColor.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBgAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                            }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = noteTitle.text,
                    hint = noteTitle.hint,
                    onFocusChanged = {
                        viewModel.onEvent(AddEditNoteEvents.ChangeTitleFocus(it))
                    },
                    onTextChanged = {
                        viewModel.onEvent(AddEditNoteEvents.EnteredTitle(it))
                    },
                    textStyle = MaterialTheme.typography.h5,
                    isHintVisible = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = noteContent.text,
                    hint = noteContent.hint,
                    onFocusChanged = {
                        viewModel.onEvent(AddEditNoteEvents.ChangeContentFocus(it))
                    },
                    onTextChanged = {
                        viewModel.onEvent(AddEditNoteEvents.EnteredContent(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}