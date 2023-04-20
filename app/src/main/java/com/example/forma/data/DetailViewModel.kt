package com.example.forma.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forma.models.Habit
import com.example.forma.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(context: Context): ViewModel() {
    private val habitRepository: HabitRepository = HabitRepository(context)
    private val taskRepository: TaskRepository = TaskRepository(context)
    private var habit: Habit? = null

    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasks: MutableStateFlow<MutableList<Task>>
        get() = _tasks

    fun loadTasks() : List<Task> {
        viewModelScope.launch {
            _tasks.value = taskRepository.getTasksByHabitId(habit?.id!!)
        }
        return _tasks.value
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.insert(habit)
            for (task in tasks.value) {
                task.habitId = habit.id
                taskRepository.insert(task)

            }
            loadHabits()
        }
        Log.d("DetailViewModel", "addHabit() called")
    }

    fun loadHabits() {
        viewModelScope.launch {
            habitRepository.getAllHabits()
            Log.d("DetailViewModel", "loadHabits() called")
        }
    }

    fun addTasks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (task in tasks.value) {
                    taskRepository.insert(task)
                }
            }
        }
        Log.d("DetailViewModel", "addTask() called")
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
            loadTasks()
        }
    }

    suspend fun updateHabit(habit: Habit) {
        habitRepository.update(habit)
        loadHabits()
    }

}