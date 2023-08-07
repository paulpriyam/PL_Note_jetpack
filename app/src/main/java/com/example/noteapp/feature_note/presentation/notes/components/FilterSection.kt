package com.example.noteapp.feature_note.presentation.notes.components

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType

@Composable
fun NoteFilterSection(
    modifier: Modifier = Modifier,
    noteFilter: NoteFilter = NoteFilter.Date(OrderType.Descending),
    onChangeFilter: (NoteFilter) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                title = "Title",
                isSelected = noteFilter is NoteFilter.Title,
                onCheck = { onChangeFilter(NoteFilter.Title(noteFilter.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Date",
                isSelected = noteFilter is NoteFilter.Date,
                onCheck = { onChangeFilter(NoteFilter.Date(noteFilter.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Color",
                isSelected = noteFilter is NoteFilter.Color,
                onCheck = { onChangeFilter(NoteFilter.Color(noteFilter.orderType)) })

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                title = "Ascending",
                isSelected = noteFilter.orderType is OrderType.Ascending,
                onCheck = { noteFilter.copy(OrderType.Ascending) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                title = "Descending",
                isSelected = noteFilter.orderType is OrderType.Descending,
                onCheck = { noteFilter.copy(OrderType.Descending) })
        }
    }
}