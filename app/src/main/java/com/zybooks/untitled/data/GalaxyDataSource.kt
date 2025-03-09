import com.zybooks.untitled.data.Galaxy

class GalaxyDataSource {
   private val galaxyList = listOf(
      Galaxy(
         galaxyid = 1,
         galaxyname = "Trigger"
      ),
      Galaxy(
         galaxyid = 2,
         galaxyname = "Amphorus"
      ),
   )

   fun getGalaxy(id: Int): Galaxy? {
      return galaxyList.find { it.galaxyid == id }
   }

   fun loadGalaxy() = galaxyList
}