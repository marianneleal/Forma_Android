package com.example.forma

import androidx.lifecycle.LiveData


class Repository(database: AppDatabase) {
    private val habitDao = database.habitDao()

    fun getAllHabits(): LiveData<List<Habit>> {
        return habitDao.getAll()
    }

//    suspend fun getHabitById(id: Int): Habit? {
//        return habitDao.getById(id)
//    }

    suspend fun addHabit(habit: Habit) {
        habitDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit)
    }
}
