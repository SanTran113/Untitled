package com.zybooks.untitled.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.data.ChapterDataSource
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.StoryDataSource
import com.zybooks.untitled.data.World
import com.zybooks.untitled.data.WorldDataSource

class StoryViewModel : ViewModel() {
    val chapterDataSource = ChapterDataSource()
    val chapterList =  mutableStateListOf<Chapter>()

    fun loadChapters(storyid: Int) {
        chapterList.clear()
        chapterList.addAll(chapterDataSource.loadChapterListFromStory(storyid))
    }

    fun addChapter(chapter: Chapter) {
        chapterList.add(chapter)
    }

    fun removeChapter(chapter: Chapter) {
        chapterList.remove(chapter)
    }

    fun getStory(id: Int): Story = StoryDataSource().getStory(id) ?: Story()
}