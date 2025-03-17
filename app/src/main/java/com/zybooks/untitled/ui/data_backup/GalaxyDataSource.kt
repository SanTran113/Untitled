import com.zybooks.untitled.R
import com.zybooks.untitled.ui.data_backup.Galaxy


class GalaxyDataSource {
    private val galaxyList = listOf(
        Galaxy(
            galaxyid = 0,
            galaxyname = "Trigger",
            imageId = R.drawable.galaxy1
        ),
        Galaxy(
            galaxyid = 1,
            galaxyname = "Amphorus",
            imageId = R.drawable.galaxy2
        ),
        Galaxy(
            galaxyid = 2,
            galaxyname = "XianZhou",
            imageId = R.drawable.galaxy3
        ),
    )

    fun getGalaxy(id: Int): Galaxy? {
        return galaxyList.find { it.galaxyid == id }
    }

    fun loadGalaxy() = galaxyList
}