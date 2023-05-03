package com.example.forma.data
import android.content.Context
import androidx.room.*
import com.example.forma.models.Converter
import com.example.forma.models.Habit
import com.example.forma.models.Task
@Database(entities = [Habit::class, Task::class], version = 3)
@TypeConverters(Converter::class)
abstract class FormaDatabase: RoomDatabase() {

    abstract val habitDao: HabitDao
    abstract val taskDao: TaskDao
}
    lateinit var INSTANCE: FormaDatabase
    fun getDatabase(context: Context): FormaDatabase {
        synchronized(FormaDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    FormaDatabase::class.java,
                    "HabitDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
        return INSTANCE
    }
