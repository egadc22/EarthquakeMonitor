package com.example.earthquakemonitor

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "eq_table")
data class Earthquake(@PrimaryKey val id: String,
                      val place: String,
                      val magnitude: Double,
                      val time: Long,
                      val latitude: Double,
                      val longitude: Double) : Parcelable