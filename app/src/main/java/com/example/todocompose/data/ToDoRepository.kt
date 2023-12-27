package com.example.todocompose.data
import com.example.todocompose.data.ToDoItem
import kotlinx.coroutines.flow.Flow
interface TodoRepository {
    fun getTodos(): List<ToDoItem>
    fun addTodo(todo: ToDoItem)
    fun updateTodo(todo: ToDoItem)
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<ToDoItem>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<ToDoItem?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: ToDoItem)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: ToDoItem)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: ToDoItem)
}