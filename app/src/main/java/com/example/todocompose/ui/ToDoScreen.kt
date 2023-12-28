package com.example.todocompose.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.todocompose.data.ToDoItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todocompose.data.TodoRepository
import com.example.todocompose.ui.TodoViewModel.ToDoItemDetails
import com.example.todocompose.ui.TodoViewModel.ToDoItemUiState
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory()
)) {
    val todos by todoViewModel.todos
    val coroutineScope = rememberCoroutineScope()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrate = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TodoInput(
            itemUiState = todoViewModel.itemUiState.value,
            onItemValueChange = { updatedDetails ->
                todoViewModel.itemUiState =
                    todoViewModel.itemUiState.value.copy(itemDetails = updatedDetails)
            },
            onSaveClick = {
                coroutineScope.launch {
                    todoViewModel.saveItem()
                }
            },
            isLandscape = isLandscape
        )

        if (isLandscape) {
            LazyRow {
                items(todos) { todo ->
                    TodoItemRow(todo = todo, onTodoClick = { todoViewModel.toggleTodoStatus(todo) })
                }
            }
        } else {
            LazyColumn {
                items(todos) { todo ->
                    TodoItemRow(todo = todo, onTodoClick = { todoViewModel.toggleTodoStatus(todo) })
                }
            }
        }
    }
}
//Chatgbt asked for generic factory code for UI for the Viewmodel as it was recommended for bug fixing
class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInput(
    itemUiState: ToDoItemUiState,
    onItemValueChange: (ToDoItemDetails) -> Unit,
    onSaveClick: () -> Unit,
    isLandscape: Boolean
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = itemUiState.itemDetails.task,
            onValueChange = {
                onItemValueChange(itemUiState.itemDetails.copy(task = it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            label = { Text("Enter task") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                onSaveClick()
            }
        }) {
            Text("Add")
        }
    }
}

@Composable
fun TodoItemRow(todo: ToDoItem, onTodoClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTodoClick() }
    ) {
        Text(text = todo.task, modifier = Modifier.weight(1f))
        Checkbox(checked = todo.isCompleted, onCheckedChange = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(todos: List<ToDoItem>, onTodoClick: (ToDoItem) -> Unit) {
    LazyColumn {
        items(todos) { todo ->
            TodoItemRow(todo = todo, onTodoClick = { onTodoClick(todo) })
        }
    }
}
