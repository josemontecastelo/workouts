package com.josealfonsomora.workouts.data.sources.room

import androidx.room.TypeConverter
import com.josealfonsomora.workouts.domain.Muscle

class Converters {
    @TypeConverter
    fun fromString(value: String): List<Muscle> {
        return value.split(",").map { Muscle.valueOf(it) }
    }

    @TypeConverter
    fun fromList(list: List<Muscle>): String {
        return list.joinToString(",")
    }
}