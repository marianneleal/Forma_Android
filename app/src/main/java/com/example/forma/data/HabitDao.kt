package com.example.forma.data

import androidx.room.*
import com.example.forma.models.Habit
import com.example.forma.models.Task

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY id DESC")
    suspend fun getAll(): List<Habit>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getById(id: Int): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit): Long

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task): Long
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTasksByHabitId(id: Int): List<Task>
}