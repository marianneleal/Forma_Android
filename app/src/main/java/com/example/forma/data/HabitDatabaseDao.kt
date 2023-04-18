package com.example.forma.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forma.models.Habit

@Dao
interface HabitDatabaseDao {

    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getById(id: Int): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

}