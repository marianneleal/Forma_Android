package com.example.forma.data

import androidx.room.*
import com.example.forma.models.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getById(id: Long): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task) : Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE habitId = :id")
    suspend fun getTasksByHabitId(id: Long): MutableList<Task>
}