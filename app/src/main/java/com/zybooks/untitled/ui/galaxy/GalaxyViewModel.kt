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
                GalaxyViewModel(application.untitledRepository)
            }
        }
    }

    private val selectedWorlds = MutableStateFlow(emptySet<World>())
    private val isWorldDialogVisible = MutableStateFlow(false)

    val uiState: StateFlow<WorldScreenUiState> = transformedFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorldScreenUiState(),
        )

    private fun transformedFlow() = combine(
        untitledRepository.getAllWorlds(),
        selectedWorlds,
        isWorldDialogVisible
    ) { worlds, selectWorlds, dialogVisible ->
        WorldScreenUiState(
            worldList = worlds,
            selectedWorlds = selectWorlds,
            isWorldDialogVisible = dialogVisible
        )
    }

    fun addWorld(title: String) {
        untitledRepository.addWorld(World(worldName = title))
    }

    fun selectWorld(world: World) {
        selectedWorlds.value = selectedWorlds.value.toMutableSet().apply {
            if (contains(world)) remove(world) else add(world)
        }
    }

    fun hideCab() {
        selectedWorlds.value = emptySet()
    }

    fun deleteSelectedWorld() {
        for (world in selectedWorlds.value) {
            untitledRepository.deleteWorld(world)
        }
        hideCab()
    }

    fun showWorldDialog() {
        isWorldDialogVisible.value = true
    }

    fun hideWorldDialog() {
        isWorldDialogVisible.value = false
    }
}

data class WorldScreenUiState(
    val worldList: List<World> = emptyList(),
    val selectedWorlds: Set<World> = emptySet(),
    val isCabVisible: Boolean = selectedWorlds.isNotEmpty(),
    val isWorldDialogVisible: Boolean = false
)