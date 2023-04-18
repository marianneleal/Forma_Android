package com.example.forma.models

import androidx.room.*
import java.util.*

@Entity(tableName = "habits")
@TypeConverters(Converter::class)
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var tasks: List<Task>,
    var color: Int,
    var dueDate: Date?
)