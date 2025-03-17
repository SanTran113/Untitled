package com.zybooks.untitled.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Galaxy (
    @PrimaryKey(autoGenerate = true)
    var galaxyId: Long = 0,

    var galaxyName: String = "",
)