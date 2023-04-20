package com.example.forma.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forma.models.Habit
import com.example.forma.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(context: Context, habitId: Long): ViewModel() {
    private val habitRepository: HabitRepository = HabitRepository(context)
    private val taskRepository: TaskRepository = TaskRepository(context)

    var loading: Boolean

    lateinit var habit: Habit

    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasks: MutableStateFlow<MutableList<Task>>
        get() = _tasks

    init {
        if (habitId > 0) {
            loading = true
            loadHabit(habitId)
        } else {
            loading = false
            habit = Habit(
                name = "New Habit",
                color = Color.LightGray.toArgb(),
                dueDate = null,
            )
        }
    }

    fun loadHabit(id: Long) {
        viewModelScope.launch {
            habit = habitRepository.getHabitById(id)!!
            Log.d("DetailViewModel", "loadHabit() called, habit: $habit")
            loadTasks()
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getTasksByHabitId(habit?.id!!)
            loading = false
        }
    }

    suspend fun save() {
        Log.d("DetailViewModel", "save() called, habit id: $habit.id, habit: $habit")

        if (habit.id == 0L) {
            habit.id = habitRepository.insert(habit)
        } else {
            habitRepository.update(habit)
        }

        // TODO: update tasks better
        for (task in tasks.value) {
            task.habitId = habit.id
            taskRepository.insert(task)
        }
    }

    fun addTask(name: String) {
        val task = Task(
            name = name,
            habitId = habit.id,
            completed = false,
        )
        _tasks.value = _tasks.value.plus(task).toMutableList()
    }

    fun deleteTask(task: Task) {
        _tasks.value = _tasks.value.minus(task) as MutableList<Task>
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    fun updateTask(task: Task) {
        task.completed = !task.completed
        viewModelScope.launch {
            taskRepository.update(task)
        }
    }
}