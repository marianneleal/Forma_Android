package com.example.forma.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forma.data.Repository
import com.example.forma.models.Habit
import com.example.forma.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitRepository: Repository = Repository(application = Application())
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: MutableStateFlow<List<Habit>>
        get() = _habits

    private var selectedHabit: Habit? = null

    init {
        viewModelScope.launch {
            loadHabits()
        }
    }

    fun onHabitClicked(habit: Habit) {
        selectedHabit = habit
        //navigateToDetailScreen()
    }

    fun onNewHabitClicked() {
        selectedHabit = null
       // navigateToDetailScreen()
    }

    fun loadTestHabits(): List<Habit>{
        return listOf<Habit>(
            Habit(name = "Test Habit 1", tasks = listOf<Task>(), color = 0, dueDate = null),
            Habit(name = "Test Habit 2", tasks = listOf<Task>(), color = 0, dueDate = null),
            Habit(name = "Test Habit 3", tasks = listOf<Task>(), color = 0, dueDate = null),
            Habit(name = "Test Habit 4", tasks = listOf<Task>(), color = 0, dueDate = null),
        )
    }



    suspend fun loadHabits() {
        _habits.value = habitRepository.getAllHabits().value ?: emptyList()
    }

    suspend fun addHabit(habit: Habit) {
        habitRepository.addHabit(habit)
        loadHabits()
    }

    suspend fun updateHabit(habit: Habit) {
        habitRepository.updateHabit(habit)
        loadHabits()
    }

    suspend fun deleteHabit(habit: Habit) {
        habitRepository.deleteHabit(habit)
        loadHabits()
    }

    override fun onCleared() {
        super.onCleared()
        // cleanup code here
    }
}
