package com.example.forma.models
import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.*


class Converter {
    @TypeConverter
    fun fromString(value: String): List<Task> {
        val listType = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Task>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return milliseconds?.let { Date(it) }
    }
}