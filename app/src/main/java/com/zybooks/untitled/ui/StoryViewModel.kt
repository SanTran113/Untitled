package com.zybooks.untitled.ui.story

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.untitled.ui.data_backup.Chapter
import com.zybooks.untitled.ui.data_backup.ChapterDataSource
import com.zybooks.untitled.ui.data_backup.Story
import com.zybooks.untitled.ui.data_backup.StoryDataSource

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