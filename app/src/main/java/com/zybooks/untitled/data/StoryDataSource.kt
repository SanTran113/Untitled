package com.zybooks.untitled.data

class StoryDataSource {
    private val storyList = listOf(
        Story(
            worldid = 1,
            storyid = 0,
            storyname = "Amp Story",
            synopsis = "This is Amp Story 1's synopsis",
            scratchpad = ""
        ),
        Story(
            worldid = 1,
            storyid = 1,
            storyname = "Amp Story 2",
            synopsis = "This is Amp Story 2's synopsis",
            scratchpad = ""
        ),
    )

    fun getStory(id: Int): Story? {
        return storyList.find { it.storyid == id }
    }

    fun getStoryListFromWorld(worldid: Int): List<Story> {
        return storyList.filter { it.worldid == worldid }
    }

    fun loadAllStories() = storyList
}