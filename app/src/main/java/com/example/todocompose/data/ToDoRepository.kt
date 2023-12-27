package com.example.todocompose.data
import com.example.todocompose.data.ToDoItem

interface TodoRepository {
    fun getTodos(): List<ToDoItem>
    fun addTodo(todo: ToDoItem)
    fun updateTodo(todo: ToDoItem)
}