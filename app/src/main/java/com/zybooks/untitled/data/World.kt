package com.zybooks.untitled.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class World (
    @PrimaryKey(autoGenerate = true)
    var worldId: Long = 0,

    var worldName: String = "",
    var imageId: Long = 0
)