package com.zybooks.untitled.ui.story

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
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.UntitledRepository
import com.zybooks.untitled.ui.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn


class StoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val untitledRepository: UntitledRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                StoryViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    untitledRepository = application.untitledRepository
                )
            }
        }
    }
    private val storyId: Long = savedStateHandle.toRoute<Routes.Story>().storyId
    private val selectedChapters = MutableStateFlow(emptySet<Chapter>())
    private val isChapterDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<StoryScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = StoryScreenUiState(),
        )

    private fun transformedFlow() = combine(
        untitledRepository.getStory(storyId).filterNotNull(),
        untitledRepository.getAllChaptersFromStoryId(storyId),
        selectedChapters,
        isChapterDialogVisible
    ) { story, chapters, selectChapter, dialogVisible ->
        StoryScreenUiState(
            story = story,
            chapList = chapters,
            selectedChapters = selectChapter,
            isChapterDialogVisible = dialogVisible
        )
    }

    fun addChapter(name: String) {
        untitledRepository.addChapter(Chapter(chapterName = name, storyId = storyId))
    }

    fun selectChapter(chapter: Chapter) {
        selectedChapters.value.contains(chapter)
    }

    fun hideCab() {
        selectedChapters.value = emptySet()
    }

    fun deleteSelectedStory() {
        for (chapter in selectedChapters.value) {
            untitledRepository.deleteStory(chapter)
        }
        hideCab()
    }

    fun showChapterDialog() {
        isChapterDialogVisible.value = true
    }

    fun hideChapterDialog() {
        isChapterDialogVisible.value = false
    }
}

data class StoryScreenUiState(
    val story: Story = Story(),
    val chapList: List<Chapter> = emptyList(),
    val selectedChapters: Set<Chapter> = emptySet(),
    val isCabVisible: Boolean = selectedChapters.isNotEmpty(),
    val isChapterDialogVisible: Boolean = false
)