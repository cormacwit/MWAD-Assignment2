package com.example.todocompose.data
import ItemDao
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao: ItemDao) : TodoRepository {
    override fun getTodos(): List<ToDoItem> {
        TODO("Not yet implemented")
    }

    override fun addTodo(todo: ToDoItem) {
        TODO("Not yet implemented")
    }

    override fun updateTodo(todo: ToDoItem) {
        TODO("Not yet implemented")
    }

    override fun getAllItemsStream(): Flow<List<ToDoItem>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<ToDoItem?> = itemDao.getItem(id)

    override suspend fun insertItem(item: ToDoItem) = itemDao.insert(item)

    override suspend fun deleteItem(item: ToDoItem) = itemDao.delete(item)

    override suspend fun updateItem(item: ToDoItem) = itemDao.update(item)
}