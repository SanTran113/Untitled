package com.zybooks.untitled.ui.chapter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.zybooks.untitled.UntitledApplication
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.data.UntitledRepository
import com.zybooks.untitled.ui.Routes
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditChapterViewModel (
    savedStateHandle: SavedStateHandle,
    private val untitledRepository: UntitledRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                EditChapterViewModel(this.createSavedStateHandle(), application.untitledRepository)
            }
        }
    }

    private val chapterId: Long = savedStateHandle.toRoute<Routes.EditChapter>().chapId

    var chapter by mutableStateOf(Chapter(0))
        private set

    fun changeChap(chap: Chapter) {
        chapter = chap
    }

    fun updateChapter() {
        untitledRepository.updateChapter(chapter)
    }

    init {
        viewModelScope.launch {
            chapter = untitledRepository.getChapter(chapterId).filterNotNull().first()
        }
    }
}