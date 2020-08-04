package com.kevalpatel2106.brew.repo.db

import androidx.room.TypeConverter
import java.util.*

internal object DbTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @JvmStatic
    @TypeConverter
    fun toLong(value: Date): Long = value.time

    @JvmStatic
    @TypeConverter
    fun toStringList(value: String?): List<String> {
        return value?.split(",")?.filter { it.isNotBlank() } ?: listOf()
    }

    @JvmStatic
    @TypeConverter
    fun toCommaSeparatedList(value: List<String>): String = value.joinToString(",")
}
