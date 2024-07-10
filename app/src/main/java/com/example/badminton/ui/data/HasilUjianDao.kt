package com.example.badminton.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HasilUjianDao {

    @Insert
    suspend fun insert(hasilUjian: HasilUjian): Long

    @Query("SELECT * FROM hasil_ujian ORDER BY id DESC")
    fun getAll(): Flow<List<HasilUjian>>
}
