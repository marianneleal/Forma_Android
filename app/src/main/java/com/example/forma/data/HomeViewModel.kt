package com.example.forma.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forma.models.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
    private val habitRepository: HabitRepository = HabitRepository(context)
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: MutableStateFlow<List<Habit>>
        get() = _habits

    private var selectedHabit: Habit? = null

    init {
        viewModelScope.launch {
            loadHabits()
        }
    }

    fun loadHabits() {
        viewModelScope.launch {
            habits.value = habitRepository.getAllHabits()
            Log.d("HomeViewModel", "loadHabits() called")
        }
    }
     fun deleteHabit(habit: Habit) {
        Log.d("VM", "deleteHabit() called")
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.delete(habit)
            loadHabits()
        }
    }

    override fun onCleared() {
        super.onCleared()
        // cleanup code here
    }

}
