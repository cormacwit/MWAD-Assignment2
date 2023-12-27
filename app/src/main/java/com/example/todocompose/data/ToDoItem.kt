package com.example.todocompose.data

import androidx.room.PrimaryKey

data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val isCompleted: Boolean = false)
