package com.example.noteapp.feature_note.domain.util

sealed class NoteFilter(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteFilter(orderType)
    class Date(orderType: OrderType) : NoteFilter(orderType)
    class Color(orderType: OrderType) : NoteFilter(orderType)
}
