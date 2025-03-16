package com.zybooks.untitled.data

import androidx.room.Dao
import androidx.room.Update

@Dao
interface GalaxyDao {

//  to update galaxy name
    @Update
    fun updateGalaxy(galaxy: Galaxy)
}