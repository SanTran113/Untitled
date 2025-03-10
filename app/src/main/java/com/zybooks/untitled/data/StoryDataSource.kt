package com.zybooks.untitled.data

class StoryDataSource {
    private val storyList = listOf(
        Story(
            worldid = 1,
            storyid = 1,
            storyname = "Amp Story",
            synopsis = "",
            scratchpad = ""
        ),
        Story(
            worldid = 1,
            storyid = 2,
            storyname = "Amp Story 2",
            synopsis = "",
            scratchpad = ""
        ),
    )

    fun getStory(id: Int): Story? {
        return storyList.find { it.storyid == id }
    }

    fun getStoryListFromWorld(worldid: Int): List<Story> {
        return storyList.filter { it.worldid == worldid }
    }

    fun loadStory() = storyList
}