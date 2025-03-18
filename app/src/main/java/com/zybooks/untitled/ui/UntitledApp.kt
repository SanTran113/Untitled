package com.zybooks.untitled.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.zybooks.untitled.ui.chapter.ChapterScreen
import com.zybooks.untitled.ui.chapter.EditChapterScreen
import com.zybooks.untitled.ui.galaxy.GalaxyScreen
import com.zybooks.untitled.ui.story.StoryScreen
import com.zybooks.untitled.ui.theme.ToDoListTheme
import com.zybooks.untitled.ui.world.WorldScreen
import kotlinx.serialization.Serializable

sealed class Routes {
   @Serializable
   data object Galaxy

   @Serializable
   data class World(
      val worldId: Long
   )

   @Serializable
   data class Story(
      val storyId: Long
   )

   @Serializable
   data class Chapter(
      val chapId: Long
   )

   @Serializable
   data class EditChapter(
      val chapId: Long
   )


}

@Composable
fun UntitledApp() {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = Routes.Galaxy
   ) {
      composable<Routes.Galaxy> {
         GalaxyScreen(
            onWorldClick = { world ->
               navController.navigate(
                  Routes.World(worldId = world.worldId)
               )
            }
         )
      }
      composable<Routes.World> { backStackEntry ->
         val routeArgs = backStackEntry.toRoute<Routes.World>()
         WorldScreen(
            onUpClick = { navController.navigateUp() },
            onStoryClick = { story ->
               navController.navigate(
                  Routes.Story(storyId = story.storyId)
               )
            }
         )
      }

      composable<Routes.Story> { backStackEntry ->
         val routeArgs = backStackEntry.toRoute<Routes.Story>()
         StoryScreen(
            onUpClick = { navController.navigateUp() },
            onChapterClick = { chapter ->
               navController.navigate(
                  Routes.Chapter(chapId = chapter.chapterId)
               )
            }
         )
      }

      composable<Routes.Chapter> { backStackEntry ->
         val routeArgs = backStackEntry.toRoute<Routes.Chapter>()
         ChapterScreen(
            onUpClick = { navController.navigateUp() },
            onEditClick = { chapId ->
               navController.navigate(
                  Routes.EditChapter(chapId = chapId)
               )
            }
         )
      }

      composable<Routes.EditChapter> {
         EditChapterScreen(
            onUpClick = { navController.navigateUp() },
            onSaveClick = { navController.navigateUp() }
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun UntitledAppPreview() {
   ToDoListTheme {
      UntitledApp()
   }
}
