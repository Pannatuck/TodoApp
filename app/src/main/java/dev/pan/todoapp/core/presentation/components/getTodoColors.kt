package dev.pan.todoapp.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.pan.todoapp.feature_todo.domain.model.TodoItem


/* We can make calls from res/Colors. This is just experiment if it'll be more useful this way*/

data class TodoItemColors(
    val backgroundColor: Color,
    val textColor: Color,
    val archiveIconColor: Color,
    val checkColor: Color
)

@Composable
fun getTodoColors(todo: TodoItem): TodoItemColors {
    return if(todo.archived){
        TodoItemColors(
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
            textColor = MaterialTheme.colorScheme.onSecondary,
            archiveIconColor = MaterialTheme.colorScheme.onSecondary,
            checkColor = if(todo.completed) MaterialTheme.colorScheme.tertiaryContainer
                else MaterialTheme.colorScheme.onSecondary
        )
    } else {
        TodoItemColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            archiveIconColor = MaterialTheme.colorScheme.secondary,
            checkColor = if(todo.completed) MaterialTheme.colorScheme.tertiaryContainer
                else MaterialTheme.colorScheme.secondary
        )
    }
}