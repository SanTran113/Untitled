package com.zybooks.untitled.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM Story WHERE storyId = :storyId")
    fun getStory(storyId: Long): Flow<Story?>

    @Query("SELECT * FROM Story WHERE world_id = :worldId ORDER BY storyId")
    fun getAllStoriesFromWorldId(worldId: Long): Flow<List<Story>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStory(story: Story): Long

    @Update
    fun updateStory(story: Story)

    @Delete
    fun deleteStory(story: Story)
}



