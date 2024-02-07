package dev.pan.todoapp.feature_todo.domain.util

sealed class TodoItemOrder(
    val sortingDirection: SortingDirection,
    val showArchived: Boolean
) {
    class Title(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Time(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Completed(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)

    fun copy(sortingDirection: SortingDirection, showArchived: Boolean) : TodoItemOrder{
        return when(this){
            // FIXME: maybe an error here of return class
            is Title -> Title(sortingDirection, showArchived)
            is Time -> Title(sortingDirection, showArchived)  // Time?
            is Completed -> Title(sortingDirection, showArchived) // Completed?
        }
    }
}