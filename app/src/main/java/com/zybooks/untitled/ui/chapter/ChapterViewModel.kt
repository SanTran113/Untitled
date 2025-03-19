package com.zybooks.untitled.ui.chapter

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class ChapterViewModel (
    savedStateHandle: SavedStateHandle,
    private val untitledRepository: UntitledRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                ChapterViewModel(
                    savedStateHandle = this.createSavedStateHandle(),
                    untitledRepository = application.untitledRepository
                )
            }
        }
    }

    val chapId: Long = savedStateHandle.toRoute<Routes.Chapter>().chapId
    private val selectedChap = MutableStateFlow(emptySet<Chapter>())
    private val isEditDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<ChapterScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ChapterScreenUiState(),
        )

    private fun transformedFlow() = combine(
        untitledRepository.getChapter(chapId).filterNotNull(),
        selectedChap,
        isEditDialogVisible
    ) { chapter, selectChap, dialogVisible->
        ChapterScreenUiState(
            chapter = chapter,
            selectedChap = selectChap,
            isEditDialogVisible = dialogVisible
        )
    }

    fun showEditDialog() {
        isEditDialogVisible.value = true
    }

    fun hideEditDialog() {
        isEditDialogVisible.value = false
    }

}

data class ChapterScreenUiState(
    val chapter: Chapter = Chapter(),
    val selectedChap: Set<Chapter> = emptySet(),
    val isCabVisible: Boolean = selectedChap.isNotEmpty(),
    val isEditDialogVisible: Boolean = false
)