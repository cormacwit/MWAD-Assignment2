package com.example.todocompose

import com.example.todocompose.data.InMemoryTodoRepository
import com.example.todocompose.data.TodoRepository
import ItemDao
import com.example.todocompose.data.OfflineItemsRepository
import com.example.todocompose.data.ToDoDatabase
import android.content.Context

interface AppContainer {
    val todoRepository: TodoRepository
}

class DefaultAppContainer (private val context: Context,
                           override val todoRepository: TodoRepository
) : AppContainer {
    val itemsRepository: TodoRepository by lazy {
        OfflineItemsRepository(ToDoDatabase.getDatabase(context).itemDao())
    }
}