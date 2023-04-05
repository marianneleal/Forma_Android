package com.example.forma
import android.content.Context
import androidx.room.*


@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDatabaseDao
}

@Database(entities = [Habit::class, Task::class], version = 1)
abstract class HabitDatabase: RoomDatabase() {

    abstract val habitDatabaseDao: HabitDatabaseDao

    private lateinit var INSTANCE: HabitDatabase
    fun getDatabase(context: Context): HabitDatabase {
        synchronized(HabitDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    HabitDatabase::class.java,
                    "CocktailMaker"
                )
                    .build()
            }
        }
        return INSTANCE
    }
}