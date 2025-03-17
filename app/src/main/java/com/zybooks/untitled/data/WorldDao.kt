package com.zybooks.untitled.data

import androidx.room.*
import com.zybooks.untitled.data.World
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldDao {
   @Query("SELECT * FROM World WHERE worldId = :worldId")
   fun getWorld(worldId: Long): Flow<World?>

   @Query("SELECT * FROM World")
   fun getAllWorlds(): Flow<List<World>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun addWorld(world: World): Long

   @Update
   fun updateWorld(world: World)

   @Delete
   fun deleteWorld(world: World)
}

