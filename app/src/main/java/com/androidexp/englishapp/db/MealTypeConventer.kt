package com.androidexp.englishapp.db

import androidx.room.TypeConverter

class MealTypeConventer {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        return attribute?.toString() ?: ""
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        return attribute ?: ""
    }
}
