package com.zybooks.untitled.ui

import androidx.lifecycle.ViewModel
import com.zybooks.untitled.ui.data_backup.Chapter
import com.zybooks.untitled.ui.data_backup.ChapterDataSource

class ChapterViewModel : ViewModel(){
    fun getChapter(id: Int): Chapter = ChapterDataSource().getChapter(id) ?: Chapter()
}