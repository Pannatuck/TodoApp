package dev.pan.todoapp.feature_todo.presentation.todo_list

import dev.pan.todoapp.feature_todo.domain.model.TodoItem
import dev.pan.todoapp.feature_todo.domain.util.SortingDirection
import dev.pan.todoapp.feature_todo.domain.util.TodoItemOrder

/* All the state values that we would need in ViewModule saved as one class from which we can pull
   all needed data. */
data class TodoListState(
    val todoItems: List<TodoItem> = emptyList(),
    val todoItemOrder: TodoItemOrder = TodoItemOrder.Time(SortingDirection.Down, true),
    val isLoading: Boolean = true,
    val error: String? = null
)
