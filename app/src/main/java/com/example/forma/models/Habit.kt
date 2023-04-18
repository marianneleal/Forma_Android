package com.example.forma.models

import androidx.room.*
import java.util.*

@Entity(tableName = "habits")
@TypeConverters(Converter::class)
data class Habit(
    var name: String,
    var color: Int,
    var dueDate: Date?,
    var tasks: List<Task>,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}