package com.example.badminton.ui.home.ujian

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.badminton.ui.data.HasilUjian
import com.example.badminton.ui.data.HasilUjianDao

@Database(entities = [HasilUjian::class], version = 1, exportSchema = false)
abstract class HasilUjianDatabase : RoomDatabase() {
    abstract fun hasilUjianDao(): HasilUjianDao

    companion object {
        @Volatile
        private var INSTANCE: HasilUjianDatabase? = null

        fun getDatabase(context: Context): HasilUjianDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HasilUjianDatabase::class.java,
                    "hasil_ujian_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
