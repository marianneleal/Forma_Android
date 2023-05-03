package com.example.forma.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "tasks",
    foreignKeys = [
        ForeignKey(entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = CASCADE)
    ]
)
data class Task(
    val name: String,
    var completed: Boolean,
    var habitId: Long,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
