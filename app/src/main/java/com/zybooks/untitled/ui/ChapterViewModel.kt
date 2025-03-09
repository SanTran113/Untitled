package com.zybooks.untitled.ui

import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.data.ChapterDataSource

class ChapterViewModel : ViewModel(){
    fun getChapter(id: Int): Chapter = ChapterDataSource().getChapter(id) ?: Chapter()
}