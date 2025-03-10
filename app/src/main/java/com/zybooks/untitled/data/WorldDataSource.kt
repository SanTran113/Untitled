package com.zybooks.untitled.data;

class WorldDataSource {
    private val worldList = listOf(
        World(
            galaxyid = 2,
            worldid = 1,
            worldname = "Amp World",
        ),
    )

    fun getWorld(id: Int): World? {
        return worldList.find { it.worldid == id }
    }

    fun loadWorlds() = worldList

}
