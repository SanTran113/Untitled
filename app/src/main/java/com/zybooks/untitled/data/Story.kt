package com.zybooks.untitled.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = Galaxy::class,
        parentColumns = ["worldId"],
        childColumns = ["world_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Story (
    @PrimaryKey(autoGenerate = true)
    var storyId: Long = 0,

    var storyName: String = "",
    var synopsis: String = "",
    var scratchPad: String = "",

    @ColumnInfo(name = "world_id")
    var worldId: Long = 0

)