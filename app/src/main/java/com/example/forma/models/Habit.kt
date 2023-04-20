package com.example.forma.models

import androidx.room.*
import java.util.*

@Entity(tableName = "habits")
@TypeConverters(Converter::class)
data class Habit(
    var name: String,
    var color: Int,
    var dueDate: Date?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}