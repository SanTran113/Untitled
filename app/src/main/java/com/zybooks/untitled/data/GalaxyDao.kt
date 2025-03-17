package com.zybooks.untitled.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface GalaxyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGalaxy(galaxy: Galaxy): Long

    //  to update galaxy name
    @Update
    fun updateGalaxy(galaxy: Galaxy)
}