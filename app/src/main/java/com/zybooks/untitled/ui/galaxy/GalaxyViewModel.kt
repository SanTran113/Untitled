package com.zybooks.untitled.ui.galaxy

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.untitled.UntitledApplication
import com.zybooks.untitled.data.UntitledRepository
import com.zybooks.untitled.data.World
import com.zybooks.untitled.ui.world.WorldViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class GalaxyViewModel (
    private val untitledRepository: UntitledRepository
): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UntitledApplication)
                WorldViewModel(application.untitledRepository)
            }
        }
    }

    private val selectedWorlds = MutableStateFlow(emptySet<World>())
    private val isWorldDialogVisable = MutableStateFlow(false)

    val uiState: StateFlow<WorldScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorldScreenUiState(),
        )

    private fun transformedFlow() = combine(
        untitledRepository.getAllWorlds(),
        selectedWorlds,
        isWorldDialogVisable
    ) { worlds, selectWorlds, dialogVisible ->
        WorldScreenUiState(
            worldList = worlds,
            selectedSubjects = selectWorlds,
            isSubjectDialogVisible = dialogVisible
        )
    }

    fun addWorld(title: String) {
        untitledRepository.addWorld(World(worldName = title))
    }

    fun selectWorld(world: World) {
        val selected = selectedWorlds.value.contains(world)
        selectedWorlds.value = if (selected) {
            selectedWorlds.value.minus(world)
        } else {
            selectedWorlds.value.plus(world)
        }
    }

    fun hideCab() {
        selectedWorlds.value = emptySet()
    }

    fun deleteSelectedWorld() {
        for (subject in selectedWorlds.value) {
            untitledRepository.deleteWorld(subject)
        }
        hideCab()
    }

    fun showWorldDialog() {
        isWorldDialogVisable.value = true
    }

    fun hideWorldDialog() {
        isWorldDialogVisable.value = false
    }
}

data class WorldScreenUiState(
    val worldList: List<World> = emptyList(),
    val selectedSubjects: Set<World> = emptySet(),
    val isCabVisible: Boolean = selectedSubjects.isNotEmpty(),
    val isSubjectDialogVisible: Boolean = false
)