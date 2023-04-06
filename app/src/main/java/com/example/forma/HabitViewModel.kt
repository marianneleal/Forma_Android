package com.example.forma

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HabitViewModel(private val habitRepository: Repository) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: MutableStateFlow<List<Habit>>
        get() = _habits

    init {
        // loadHabits()
    }

    suspend fun loadHabits(): List<Habit> {
        return habitRepository.getAllHabits().value ?: emptyList()
    }

    suspend fun addHabit(habit: Habit) {
        habitRepository.addHabit(habit)
    }


    suspend fun updateHabit(habit: Habit) {
        habitRepository.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
    }

    fun onDestroy() {
        // cleanup code here
    }
}
