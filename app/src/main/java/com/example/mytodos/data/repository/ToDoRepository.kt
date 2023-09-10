package com.example.mytodos.data.repository

import androidx.lifecycle.LiveData
import com.example.mytodos.data.ToDoDao
import com.example.mytodos.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun insertData(todoData: ToDoData) {
        toDoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: ToDoData) {
        toDoDao.updateData(todoData)
    }

    suspend fun  deleteItem(todoData: ToDoData) {
        toDoDao.deleteItem(todoData )
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDatabase(searchQuery)
    }
}