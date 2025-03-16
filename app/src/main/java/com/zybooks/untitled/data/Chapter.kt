package com.zybooks.untitled.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = Galaxy::class,
        parentColumns = ["storyId"],
        childColumns = ["story_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Chapter (
    @PrimaryKey(autoGenerate = true)
    var chapterId: Long = 0,

    var chapterName: String = "",
    var chapterBody: String = "",
    var wordCount: Long = 0,

    @ColumnInfo(name = "story_id")
    var storyId: Long = 0

)