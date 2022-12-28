package com.example.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.feature_note.domain.util.NoteFilter
import com.example.noteapp.feature_note.domain.util.OrderType

@Composable
fun NoteFilterSection(
    modifier: Modifier = Modifier,
    noteFilter: NoteFilter = NoteFilter.Date(OrderType.Descending),
    onFilterChanged: (NoteFilter) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                isChecked = noteFilter is NoteFilter.Title,
                onCheck = { onFilterChanged(NoteFilter.Title(noteFilter.orderType)) })

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Date",
                isChecked = noteFilter is NoteFilter.Date,
                onCheck = { onFilterChanged(NoteFilter.Date(noteFilter.orderType)) })

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Color",
                isChecked = noteFilter is NoteFilter.Color,
                onCheck = { onFilterChanged(NoteFilter.Color(noteFilter.orderType)) })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                isChecked = noteFilter.orderType is OrderType.Ascending,
                onCheck = { /*TODO*/ })

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text ="Descending",
                isChecked = noteFilter.orderType is OrderType.Descending,
                onCheck = { /*TODO*/ })
        }
    }
}