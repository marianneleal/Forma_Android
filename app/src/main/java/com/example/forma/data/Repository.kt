package com.example.forma.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forma.models.Habit


class Repository(application: Application) {
    private val database = getDatabase(application)
    private val habitDao = database.habitDao

    fun getAllHabits(): List<Habit> {
        return habitDao.getAll()
    }

    suspend fun getHabitById(id: Int): Habit? {
        return habitDao.getById(id)
    }

    suspend fun addHabit(habit: Habit) {
        Log.d("Repository", "addHabit() called")
        habitDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit)
    }
}
