package com.example.badminton.ui.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kursus(
    val name: String,
    val description: String,
    val photo: Int,
    val videoUrl: String
) : Parcelable
