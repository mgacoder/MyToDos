package com.example.mytodos.data.repository

import androidx.lifecycle.LiveData
import com.example.mytodos.data.ToDoDao
import com.example.mytodos.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(todoData: ToDoData) {
        toDoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: ToDoData) {
        toDoDao.updateData(todoData)
    }
}