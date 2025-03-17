package com.zybooks.untitled.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.untitled.ui.theme.ToDoListTheme
import kotlinx.serialization.Serializable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.toRoute
import com.zybooks.untitled.data_backup.StoryDataSource
import com.zybooks.untitled.data_backup.WorldDataSource
import com.zybooks.untitled.ui.galaxy.GalaxyScreen
import com.zybooks.untitled.ui.story.StoryViewModel

sealed class Routes {
   @Serializable
   data object Galaxy

   @Serializable
   data class World(
      val worldId: Int
   )

   @Serializable
   data class Story(
      val storyId: Int
   )

   @Serializable
   data class Chapter(
      val chapterId: Int
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
            onImageClick = { world ->
               navController.navigate(
                  Routes.World(world.galaxyid)
               )
            },
            onWorldButtonClick = { }
         )
      }
      composable<Routes.World> { backstackEntry ->
         val world: Routes.World = backstackEntry.toRoute()

         WorldScreen(
            worldId = world.worldId,
            onStoryClick = { storyId ->
               navController.navigate(
                  Routes.Story(storyId)
               )
            },
            onUpClick = {
               navController.navigateUp()
            },
            onStoryButtonClick = {}
         )
      }
      composable<Routes.Story> { backstackEntry ->
         val story: Routes.Story = backstackEntry.toRoute()

         StoryScreen(
            storyId = story.storyId,
            onChapterClick = { chapterId ->
               navController.navigate(
                  Routes.Chapter(chapterId)
               )
            },
            onUpClick = {
               navController.navigateUp()
            },
            onChapterButtonClick = { }
         )
      }

      composable<Routes.Chapter> { backstackEntry ->
         val chapter: Routes.Chapter = backstackEntry.toRoute()

         ChapterScreen(
            chapterId = chapter.chapterId,
            onUpClick = {
               navController.navigateUp()
            }
         )

      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UntitledAppBar(
   title: String,
   modifier: Modifier = Modifier,
   canNavigateBack: Boolean = false,
   onUpClick: () -> Unit = { },
) {
   CenterAlignedTopAppBar (
      modifier = Modifier.padding(10.dp),
      title = { Text(
         title,
         style = MaterialTheme.typography.headlineLarge,
         fontWeight = FontWeight.SemiBold,
      ) },
      colors = TopAppBarDefaults.topAppBarColors(
//         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      navigationIcon = {
         if (canNavigateBack) {
            IconButton(onClick = onUpClick) {
               Icon(Icons.Filled.ArrowBack, "Back")
            }
         }
      }
   )
}

@Composable
fun BottomButton(
   text: String,
   onClick: () -> Unit = { }
) {
   ExtendedFloatingActionButton(
      onClick = { onClick() },
      icon = { Icon(Icons.Filled.Add, "BottomButton") },
      text = { Text(
         text = text,
         fontFamily = FontFamily.SansSerif,
         fontWeight = FontWeight.SemiBold
      ) },
      shape = RoundedCornerShape(50)
   )
}


@Composable
fun ChapterScreen(
   chapterId: Int,
   modifier: Modifier = Modifier,
   viewModel: ChapterViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   val chapter = viewModel.getChapter(chapterId)

   Scaffold(
      topBar = {
         UntitledAppBar(
            canNavigateBack = true,
            onUpClick = onUpClick,
            title = chapter.chaptername
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding)
      ) {

      }
   }
}


@Composable
fun ExpandableSectionTitle(
   modifier: Modifier = Modifier,
   isExpanded: Boolean,
   title: String
) {
   val icon = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown
   val contentDescription = if (isExpanded) "Collapse section" else "Expand section"

   Row(
      modifier = modifier
         .fillMaxWidth()
         .clip(shape = RoundedCornerShape(50.dp))
         .padding(15.dp),

      verticalAlignment = Alignment.CenterVertically,

   ) {
      Image(
         modifier = Modifier.size(32.dp),
         imageVector = icon,
//         colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
         contentDescription = contentDescription
      )
      Text(
         text = title,
         style = MaterialTheme.typography.headlineSmall,
         modifier = Modifier.padding(start = 8.dp),
         fontWeight = FontWeight.Medium
      )
   }
}


@Composable
fun ExpandableSection(
   modifier: Modifier = Modifier,
   title: String,
   content: @Composable () -> Unit
) {
   var isExpanded by rememberSaveable { mutableStateOf(false) }
   Column(
      modifier = modifier
         .clickable { isExpanded = !isExpanded }
         .background(color = MaterialTheme.colorScheme.primaryContainer)
         .fillMaxWidth(0.9f)
   ) {
      ExpandableSectionTitle(isExpanded = isExpanded, title = title)

      AnimatedVisibility(
         modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
         visible = isExpanded
      ) {
         content()
      }
   }
}

@Preview
@Composable
fun PreviewGalaxyScreen() {
   ToDoListTheme {
      GalaxyScreen(
         onImageClick = {},
         onWorldButtonClick = {},
      )
   }
}

@Preview
@Composable
fun PreviewWorldScreen() {
   val world = WorldDataSource().loadWorlds()[1]
   ToDoListTheme {
      WorldScreen(
         worldId = world.worldid,
         onStoryClick = {},
         onStoryButtonClick = {}
      )
   }
}

@Preview
@Composable
fun PreviewStoryScreen() {
   val story = StoryDataSource().loadAllStories()[0]
   ToDoListTheme {
      StoryScreen(
         storyId = story.storyid,
         onChapterClick = {},
         onChapterButtonClick = {}
      )
   }
}

@Preview
@Composable
fun PreviewChapterScreen() {
   ToDoListTheme {
      ChapterScreen(
         chapterId = 1
      )
   }
}


//@Preview(showBackground = true)
//@Composable
//fun ToDoScreenPreview() {
//   val viewModel = ToDoViewModel()
//   viewModel.createTestTasks()
//   ToDoListTheme {
//      ToDoScreen(todoViewModel = viewModel)
//   }
//}

