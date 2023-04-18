package com.example.forma.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forma.models.Habit

@Dao
interface HabitDatabaseDao {

    @Query("SELECT * FROM habits")
    fun getAll(): List<Habit>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getById(id: Int): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

}