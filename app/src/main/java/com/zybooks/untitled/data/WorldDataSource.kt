package com.zybooks.untitled.data;

class WorldDataSource {
    private val worldList = listOf(
        World(
            galaxyid = 0,
            worldid = 0,
            worldname = "Trigger World",
        ),
        World(
            galaxyid = 1,
            worldid = 1,
            worldname = "Amp World",
        ),
    )

    fun getWorld(id: Int): World? {
        return worldList.find { it.worldid == id }
    }

    fun loadWorlds() = worldList

}
