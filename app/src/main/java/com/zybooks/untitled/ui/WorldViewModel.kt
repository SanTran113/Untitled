package com.zybooks.untitled.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.untitled.ui.data_backup.Story
import com.zybooks.untitled.ui.data_backup.StoryDataSource
import com.zybooks.untitled.ui.data_backup.World
import com.zybooks.untitled.ui.data_backup.WorldDataSource

class WorldViewModel : ViewModel() {
    private val storyDataSource = StoryDataSource()
    private val worldDataSource = WorldDataSource()

    var storyList = mutableStateListOf<Story>()
        private set


    fun getWorld(id: Int): World = worldDataSource.getWorld(id) ?: World()

    fun loadStories(worldId: Int) {
        storyList.clear()
        storyList.addAll(storyDataSource.getStoryListFromWorld(worldId))
    }

    fun addStory(story: Story) {
        storyList.add(story)
    }

    fun removeStory(story: Story) {
        storyList.remove(story)
    }
}