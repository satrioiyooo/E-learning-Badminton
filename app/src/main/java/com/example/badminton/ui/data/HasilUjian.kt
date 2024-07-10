package com.example.badminton.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hasil_ujian")
data class HasilUjian(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nilai: Int,
    val hasil: String
)
