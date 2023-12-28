package com.example.todocompose.data
import com.example.todocompose.data.ToDoItem
import kotlinx.coroutines.flow.Flow

class InMemoryTodoRepository : TodoRepository {
    private var todoList = mutableListOf<ToDoItem>()

    override fun getTodos(): List<ToDoItem> = todoList.toList()

    override fun addTodo(todo: ToDoItem) {
        todoList.add(todo)
    }

    override fun updateTodo(todo: ToDoItem) {
        val index = todoList.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            todoList[index] = todo
        }
    }

    override fun getAllItemsStream(): Flow<List<ToDoItem>> {
        TODO("Not yet implemented")
    }

    override fun getItemStream(id: Int): Flow<ToDoItem?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertItem(item: ToDoItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: ToDoItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: ToDoItem) {
        TODO("Not yet implemented")
    }
}