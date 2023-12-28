package com.example.todocompose.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todocompose.ToDoApplication
import com.example.todocompose.data.ToDoItem
import com.example.todocompose.data.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val todos = mutableStateOf<List<ToDoItem>>(emptyList())
    var itemUiState = mutableStateOf(ToDoItemUiState())

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

    fun ToDoItemDetails.toToDoItem(): ToDoItem = ToDoItem(
        id = id,
        task = task,
        isCompleted = isCompleted
    )

    fun ToDoItem.toToDoItemUiState(isEntryValid: Boolean = false): ToDoItemUiState = ToDoItemUiState(
        itemDetails = this.toToDoItemDetails(),
        isEntryValid = isEntryValid
    )

    fun ToDoItem.toToDoItemDetails(): ToDoItemDetails = ToDoItemDetails(
        id = id,
        task = task,
        isCompleted = isCompleted
    )

    fun ToDoItemUiState.toToDoItem(): ToDoItem = itemDetails.toToDoItem()

    fun ToDoItemUiState.toToDoItemDetails(): ToDoItemDetails = itemDetails

    suspend fun saveItem() {
        if (validateInput()) {
            repository.insertItem(itemUiState.value.itemDetails.toToDoItem())
        }
    }

    // Removed repeated functions

    // New function to save item with coroutine scope
    suspend fun saveItemWithCoroutine() {
        if (validateInput()) {
            repository.insertItem(itemUiState.value.itemDetails.toToDoItem())
        }
    }
}
