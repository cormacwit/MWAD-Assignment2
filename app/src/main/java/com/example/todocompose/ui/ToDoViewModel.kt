package com.example.todocompose.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todocompose.ToDoApplication
import com.example.todocompose.data.ToDoItem
import com.example.todocompose.data.TodoRepository

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val todos = mutableStateOf<List<ToDoItem>>(emptyList())
    val itemUiState = mutableStateOf(ToDoItemUiState())

    init {
        refreshTodos()
    }

    fun addTodo() {
        val newTodo = itemUiState.value.toToDoItem()
        repository.addTodo(newTodo)
        refreshTodos()
    }

    fun toggleTodoStatus(todo: ToDoItem) {
        val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
        repository.updateTodo(updatedTodo)
        refreshTodos()
    }

    private fun refreshTodos() {
        todos.value = repository.getTodos()
    }

    fun validateInput(uiState: ToDoItemDetails = itemUiState.value.itemDetails): Boolean {
        return with(uiState) {
            task.isNotBlank() && !isCompleted
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication)
                val todoRepository = application.container.todoRepository
                TodoViewModel(repository = todoRepository)
            }
        }
    }

    data class ToDoItemUiState(
        val itemDetails: ToDoItemDetails = ToDoItemDetails(),
        val isEntryValid: Boolean = false
    )

    data class ToDoItemDetails(
        val id: Int = 0,
        val task: String = "",
        val isCompleted: Boolean = false
    )

    /**
     * Extension function to convert [ToDoItemDetails] to [ToDoItem].
     */
    fun ToDoItemDetails.toToDoItem(): ToDoItem = ToDoItem(
        id = id,
        task = task,
        isCompleted = isCompleted
    )

    /**
     * Extension function to format the task.
     */
    fun ToDoItem.formattedTask(): String {
        // You can add any formatting logic here if needed
        return task
    }

    /**
     * Extension function to convert [ToDoItem] to [ToDoItemUiState].
     */
    fun ToDoItem.toToDoItemUiState(isEntryValid: Boolean = false): ToDoItemUiState = ToDoItemUiState(
        itemDetails = this.toToDoItemDetails(),
        isEntryValid = isEntryValid
    )

    /**
     * Extension function to convert [ToDoItem] to [ToDoItemDetails].
     */
    fun ToDoItem.toToDoItemDetails(): ToDoItemDetails = ToDoItemDetails(
        id = id,
        task = task,
        isCompleted = isCompleted
    )

    // New functions for handling UI state
    fun ToDoItemUiState.toToDoItem(): ToDoItem = itemDetails.toToDoItem()

    fun ToDoItemUiState.toToDoItemDetails(): ToDoItemDetails = itemDetails

    suspend fun saveItem() {
        if (validateInput()) {
            repository.insertItem(itemUiState.value.itemDetails.toToDoItem())
        }
    }
}



