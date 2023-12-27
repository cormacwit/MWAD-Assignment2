package com.example.todocompose.data
import com.example.todocompose.data.ToDoItem
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
}