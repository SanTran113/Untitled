package com.zybooks.untitled.data

class ChapterDataSource {
    private val chapterList = listOf(
        Chapter(
            storyid = 0,
            chapterid = 0,
            chaptername = "Trigger Chap 1",
            chapterbody = "",
            wordcount = 0
        ),
        Chapter(
            storyid = 0,
            chapterid = 1,
            chaptername = "Trigger Chap 2",
            chapterbody = "",
            wordcount = 0
        ),
        Chapter(
            storyid = 0,
            chapterid = 2,
            chaptername = "Trigger Chap 3",
            chapterbody = "",
            wordcount = 0
        ),
        Chapter(
            storyid = 1,
            chapterid = 3,
            chaptername = "Amphorus Chap 1",
            chapterbody = "",
            wordcount = 0
        ),
    )

    fun getChapter(id: Int): Chapter? {
        return chapterList.find { it.chapterid == id }
    }

    fun loadChapterListFromStory(storyId: Int): List<Chapter> {
        return chapterList.filter { it.storyid == storyId }
    }

}
