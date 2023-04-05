package com.example.forma

import HabitUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HabitViewModel(private val habitUseCase: HabitUseCase) : ViewModel() {

    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>>
        get() = _habits

    init {
        loadHabits()
    }

    fun loadHabits() {
        viewModelScope.launch {
            _habits.value = habitUseCase.getAllHabits()
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            habitUseCase.addHabit(habit)
            loadHabits()
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitUseCase.deleteHabit(habit)
            loadHabits()
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            habitUseCase.updateHabit(habit)
            loadHabits()
        }
    }

    override fun onCleared() {
        super.onCleared()
        habitUseCase.onDestroy()
    }
}
