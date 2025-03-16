package com.zybooks.studyhelper.data

import androidx.room.*
import com.zybooks.untitled.data.World
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldDao {
   @Query("SELECT * FROM World WHERE worldId = :worldId")
   fun getWorld(worldId: Long): Flow<World?>

   @Query("SELECT * FROM World")
   fun getAllWorlds(): Flow<World?>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun addWorld(subject: World): Long

   @Update
   fun updateWorld(subject: World)

   @Delete
   fun deleteWorld(subject: World)
}

