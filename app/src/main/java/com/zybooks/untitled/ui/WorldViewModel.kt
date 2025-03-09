package com.zybooks.untitled.ui

import androidx.lifecycle.ViewModel
import com.zybooks.untitled.data.World
import com.zybooks.untitled.data.WorldDataSource

class WorldViewModel : ViewModel() {
    fun getWorld(id: Int): World = WorldDataSource().getWorld(id) ?: World()
}