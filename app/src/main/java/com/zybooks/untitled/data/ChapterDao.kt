package com.zybooks.untitled.data

import androidx.room.*
import com.zybooks.untitled.data.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM Chapter WHERE chapterId = :chapterId")
    fun getChapter(chapterId: Long): Flow<Chapter?>

    @Query("SELECT * FROM Chapter WHERE story_id = :storyId ORDER BY chapterId")
    fun getAllChaptersFromStoryId(storyId: Long): Flow<List<Chapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addChapter(chap: Chapter): Long

    @Update
    fun updateChapter(chap: Chapter)

    @Query("UPDATE Chapter SET wordCount = :wordCount WHERE chapterId = :chapterId")
    fun updateWordCount(chapterId: Long, wordCount: Int)


    @Delete
    fun deleteChapter(chap: Chapter)
}
