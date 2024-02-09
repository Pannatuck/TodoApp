package dev.pan.todoapp.feature_todo.domain.util

sealed class SortingDirection {
    object Up: SortingDirection()
    object Down: SortingDirection()
}