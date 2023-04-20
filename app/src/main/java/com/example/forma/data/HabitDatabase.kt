package com.example.forma.data
import android.content.Context
import androidx.room.*
import com.example.forma.models.Converter
import com.example.forma.models.Habit
import com.example.forma.models.Task


@Database(entities = [Habit::class, Task::class], version = 3)
@TypeConverters(Converter::class)
abstract class HabitDatabase: RoomDatabase() {

    abstract val habitDao: HabitDao
    abstract val taskDao: TaskDao
}
    lateinit var INSTANCE: HabitDatabase
    fun getDatabase(context: Context): HabitDatabase {
        synchronized(HabitDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    HabitDatabase::class.java,
                    "HabitDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
        return INSTANCE
    }
