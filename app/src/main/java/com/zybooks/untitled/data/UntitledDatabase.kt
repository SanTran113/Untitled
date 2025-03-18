package com.zybooks.untitled.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [World::class, Story::class, Chapter::class], version = 1)
abstract class UntitledDatabase : RoomDatabase() {

    abstract fun worldDao(): WorldDao
    abstract fun storyDao(): StoryDao
    abstract fun chapterDao(): ChapterDao
}