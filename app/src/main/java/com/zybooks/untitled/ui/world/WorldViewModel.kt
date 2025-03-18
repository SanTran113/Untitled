package com.zybooks.untitled.ui.world

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
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.UntitledRepository
import com.zybooks.untitled.data.World
import com.zybooks.untitled.ui.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class WorldViewModel(
    savedStateHandle: SavedStateHandle,
    private val untitledRepository: UntitledRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                WorldViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    untitledRepository = application.untitledRepository
                )
            }
        }
    }
    private val worldId: Long = savedStateHandle.toRoute<Routes.World>().worldId
    private val selectedStories = MutableStateFlow(emptySet<Story>())
    private val isStoryDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<WorldScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorldScreenUiState(),
        )

    private fun transformedFlow() = combine(
        untitledRepository.getWorld(worldId).filterNotNull(),
        untitledRepository.getAllStoriesFromWorldId(worldId),
        selectedStories,
        isStoryDialogVisible
    ) { world, stories, selectStories, dialogVisible ->
        WorldScreenUiState(
            world = world,
            storyList = stories,
            selectedStories = selectStories,
            isWorldDialogVisible = dialogVisible
        )
    }

    fun addStory(name: String) {
        untitledRepository.addStory(Story(storyName = name, worldId = worldId))
    }

    fun selectStory(story: Story) {
        selectedStories.value.contains(story)
    }

    fun hideCab() {
        selectedStories.value = emptySet()
    }

    fun deleteSelectedStory() {
        for (story in selectedStories.value) {
            untitledRepository.deleteStory(story)
        }
        hideCab()
    }

    fun showStoryDialog() {
        isStoryDialogVisible.value = true
    }

    fun hideStoryDialog() {
        isStoryDialogVisible.value = false
    }
}

data class WorldScreenUiState(
    val world: World = World(),
    val storyList: List<Story> = emptyList(),
    val selectedStories: Set<Story> = emptySet(),
    val isCabVisible: Boolean = selectedStories.isNotEmpty(),
    val isWorldDialogVisible: Boolean = false
)