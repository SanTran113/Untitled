package com.zybooks.untitled.ui

import GalaxyDataSource
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data.World
import com.zybooks.untitled.data.WorldDataSource

class GalaxyViewModel : ViewModel() {
    val galaxyDataSource = GalaxyDataSource().loadGalaxy()
    val worldDataSource = WorldDataSource()
    val worldList =  mutableStateListOf<World>()



    fun loadWorlds() {
        worldList.clear()
        worldList.addAll(worldDataSource.loadWorlds())
    }

    fun addWorld(world: World) {
        worldList.add(world)
    }

    fun removeStory(world: World) {
        worldList.remove(world)
    }
}