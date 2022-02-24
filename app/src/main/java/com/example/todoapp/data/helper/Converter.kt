package com.example.todoapp.data.helper

import androidx.room.TypeConverter
import com.example.todoapp.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}