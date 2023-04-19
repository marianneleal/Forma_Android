package com.example.forma.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks",
    foreignKeys = [
        ForeignKey(entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = CASCADE)
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    var completed: Boolean,
    val habitId: Int,
)
