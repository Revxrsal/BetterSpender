package com.revxrsal.betterspender.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun LocalDate.toLong() = toEpochDay()

    @TypeConverter
    fun Long.toLocalDate() = LocalDate.ofEpochDay(this)!!
}