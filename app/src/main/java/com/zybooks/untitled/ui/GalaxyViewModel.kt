package com.zybooks.untitled.ui

import GalaxyDataSource
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data_backup.World
import com.zybooks.untitled.data_backup.WorldDataSource

class GalaxyViewModel : ViewModel() {
    val galaxyDataSource = GalaxyDataSource().loadGalaxy()
    val worldDataSource = WorldDataSource()
    val worldList =  mutableStateListOf<World>()

    fun addWorld(world: World) {
        worldList.add(world)
    }

    fun removeStory(world: World) {
        worldList.remove(world)
    }
}