package com.example.forma.data
import android.content.Context
import com.example.forma.models.Task

class TaskRepository(context: Context) {
    private val database = getDatabase(context)
    private val taskDao = database.taskDao

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAll()
    }

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun getTasksByHabitId(id: Long): MutableList<Task> {
        return taskDao.getTasksByHabitId(id)
    }
}