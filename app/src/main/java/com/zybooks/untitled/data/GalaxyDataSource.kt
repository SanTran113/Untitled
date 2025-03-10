import com.zybooks.todolist.R
import com.zybooks.untitled.data.Galaxy

class GalaxyDataSource {
   private val galaxyList = listOf(
      Galaxy(
         galaxyid = 1,
         galaxyname = "Trigger",
         imageId = R.drawable.galaxy1
      ),
      Galaxy(
         galaxyid = 2,
         galaxyname = "Amphorus",
         imageId = R.drawable.galaxy2
      ),
      Galaxy(
         galaxyid = 3,
         galaxyname = "XianZhou",
         imageId = R.drawable.galaxy3
      ),
   )

   fun getGalaxy(id: Int): Galaxy? {
      return galaxyList.find { it.galaxyid == id }
   }

   fun loadGalaxy() = galaxyList
}