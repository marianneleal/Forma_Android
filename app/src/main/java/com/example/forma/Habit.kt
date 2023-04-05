package com.example.forma

import androidx.room.*
import java.util.*

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val tasks: List<Task>,
    val color: Int,
    var isCompleted: Boolean,
    var dueDate: Date?
)