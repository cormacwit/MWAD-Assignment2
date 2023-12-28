package com.example.todocompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import com.example.todocompose.data.ToDoItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todocompose.ui.TodoViewModel.ToDoItemDetails
import com.example.todocompose.ui.TodoViewModel.ToDoItemUiState
import kotlinx.coroutines.launch

@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel(factory = TodoViewModel.Factory)) {
    val todos by todoViewModel.todos
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TodoInput(
                itemUiState = todoViewModel.itemUiState.value,
                onItemValueChange = { updatedDetails ->
                    todoViewModel.itemUiState =
                        todoViewModel.itemUiState.itemDetails.copy(itemDetails = updatedDetails)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        todoViewModel.saveItem()
                    }
                }
            )
        }

        items(todos.size) { index ->
            val todo = todos[index]
            TodoItemRow(todo = todo, onTodoClick = { todoViewModel.toggleTodoStatus(todo) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInput(
    itemUiState: ToDoItemUiState,
    onItemValueChange: (ToDoItemDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        OutlinedTextField(
            value = itemUiState.itemDetails.task,
            onValueChange = {
                onItemValueChange(itemUiState.itemDetails.copy(task = it))
            },
            modifier = Modifier.weight(1f),
            label = { Text("Enter task") },
        )
        Spacer(modifier = Modifier.width(8.dp))
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
