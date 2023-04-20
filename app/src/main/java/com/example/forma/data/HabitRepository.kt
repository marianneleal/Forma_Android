package com.example.forma.data

import android.content.Context
import android.util.Log
import com.example.forma.models.Habit
import com.example.forma.models.Task


class HabitRepository(context: Context) {
    private val database = getDatabase(context)
    private val habitDao = database.habitDao

    suspend fun getAllHabits(): List<Habit> {
        return habitDao.getAll()
    }

    suspend fun getHabitById(id: Long): Habit? {
        return habitDao.getById(id)
    }

    suspend fun insert(habit: Habit): Long {
        Log.d("Repository", "addHabit() called")
        return habitDao.insert(habit)
    }

    suspend fun update(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun delete(habit: Habit) {
        Log.d("Repository", "deleteHabit() called")
        habitDao.delete(habit)
    }
    suspend fun getTasksByHabitId(id: Int): List<Task> {
        return habitDao.getTasksByHabitId(id)
    }
}
