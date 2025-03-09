package com.zybooks.untitled.ui

import GalaxyDataSource
import androidx.lifecycle.ViewModel

class GalaxyViewModel : ViewModel() {
    val galaxyList = GalaxyDataSource().loadGalaxy()
}