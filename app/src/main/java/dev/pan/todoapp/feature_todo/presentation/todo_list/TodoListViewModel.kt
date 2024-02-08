package dev.pan.todoapp.feature_todo.presentation.todo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.pan.todoapp.core.util.TodoListStrings
import dev.pan.todoapp.feature_todo.data.di.IoDispatcher
import dev.pan.todoapp.feature_todo.domain.model.TodoItem
import dev.pan.todoapp.feature_todo.domain.use_case.TodoUseCaseResult
import dev.pan.todoapp.feature_todo.domain.use_case.TodoUseCases
import dev.pan.todoapp.feature_todo.domain.util.TodoItemOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(TodoListState()) // adding private let UI read state, but not modify
    val state: State<TodoListState> = _state

    private var undoTodoItem: TodoItem? = null
    private var getTodoItemJob: Job? = null
    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    fun onEvent(event: TodoListEvent){
        when(event){
            is TodoListEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.deleteTodoItem(event.todo)
                    getTodoItems() // update list in UI
                    undoTodoItem = event.todo
                }
            }
            is TodoListEvent.Sort -> {
                val stateOrderAlreadyMatchesEventOrder =  // check if state order already matches event order
                    _state.value.todoItemOrder::class == event.todoItemOrder::class &&
                    _state.value.todoItemOrder.showArchived == event.todoItemOrder.showArchived &&
                    _state.value.todoItemOrder.sortingDirection == event.todoItemOrder.sortingDirection

                if(stateOrderAlreadyMatchesEventOrder){
                    return
                }

                _state.value = _state.value.copy(
                    todoItemOrder = event.todoItemOrder
                )
                getTodoItems()
            }
            is TodoListEvent.ToggleArchived -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.toggleArchiveTodoItem(event.todo)
                    getTodoItems()
                }
            }
            is TodoListEvent.ToggleCompleted -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.toggleCompleteTodoItem(event.todo)
                    getTodoItems()
                }
            }
            TodoListEvent.UndoDelete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.addTodoItem(undoTodoItem ?: return@launch) // it can't be null in this scenario, but just in case
                    undoTodoItem = null // updating value back to null after returning deleted item
                    getTodoItems()
                }
            }
        }
    }

    fun getTodoItems(){
        getTodoItemJob?.cancel() // need to prevent memory leak if another Job is already running

        getTodoItemJob = viewModelScope.launch(dispatcher + errorHandler) {
            val result = todoUseCases.getTodoItems(
                todoItemOrder = _state.value.todoItemOrder
            )
            when(result){
                is TodoUseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        todoItems = result.todoItems,
                        todoItemOrder = _state.value.todoItemOrder,
                        isLoading = false
                    )
                }
                is TodoUseCaseResult.Error -> {
                    _state.value = _state.value.copy(
                        error = TodoListStrings.CANT_GET_TODOS,
                        isLoading = false
                    )
                }
            }
        }
    }
}