package com.androidexp.englishapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidexp.englishapp.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConventer::class) // Chú ý rằng tên đúng là MealTypeConverter
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration() // Cần xem xét cho môi trường sản xuất
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
