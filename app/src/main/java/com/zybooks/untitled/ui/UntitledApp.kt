package com.zybooks.untitled.ui

import android.adservices.adid.AdId
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.untitled.Task
import com.zybooks.untitled.data.World
import com.zybooks.untitled.ui.theme.ToDoListTheme
import kotlinx.serialization.Serializable
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.toRoute
import com.zybooks.untitled.data.ChapterDataSource
import com.zybooks.untitled.data.Galaxy
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.data.StoryDataSource
import com.zybooks.untitled.data.WorldDataSource

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
            }
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
            }
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
            }
         )
      }

      composable<Routes.Chapter> { backstackEntry ->
         val chapter: Routes.Chapter = backstackEntry.toRoute()

         // Add your ChapterScreen composable here
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
fun PetAppBar(
   title: String,
   modifier: Modifier = Modifier,
   canNavigateBack: Boolean = false,
   onUpClick: () -> Unit = { },
) {
   TopAppBar(
      title = { Text(title) },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier,
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
fun GalaxyScreen(
   onImageClick: (Galaxy) -> Unit,
   modifier: Modifier = Modifier,
   viewModel: GalaxyViewModel = viewModel()
) {
   Scaffold(
// will contain the bottom app bar
   ) { innerPadding ->
      Column {
         Text(
            modifier = Modifier.padding(8.dp),
            text = "Welcome to Your Galaxy",
         )

         LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.padding(innerPadding)
         ) {
            items(viewModel.galaxyDataSource) { galaxy ->
               Image(
                  painter = painterResource(id = galaxy.imageId),
                  contentDescription = "Part of ${galaxy.galaxyname}",
                  modifier = Modifier.clickable(
                     onClick = { onImageClick(galaxy) },
                     onClickLabel = "Select the world"
                  )
               )
            }
         }
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
         .padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
   ) {
      Image(
         modifier = Modifier.size(32.dp),
         imageVector = icon,
         colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
         contentDescription = contentDescription
      )
      Text(
         text = title,
         style = MaterialTheme.typography.headlineMedium,
         modifier = Modifier.padding(start = 8.dp)
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
         .fillMaxWidth()
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

@Composable
fun WorldScreen(
   worldId: Int,
   onStoryClick: (Int) -> Unit,
   modifier: Modifier = Modifier,
   viewModel: WorldViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   val world = viewModel.getWorld(worldId)
   viewModel.loadStories(worldId)
   val storyList = viewModel.storyList

   Scaffold(
      topBar = {
         PetAppBar(
            onUpClick = onUpClick,
            title = world.worldname,
            canNavigateBack = true,
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding)
      ) {
         LazyColumn {
            items(storyList) { story ->
               ExpandableSection(modifier = modifier, title = story.storyname) {
                  Column {
                     Text(
                        modifier = Modifier.padding(8.dp),
                        text = story.synopsis,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                     )
                     Text(
                        modifier = Modifier
                           .clickable { onStoryClick(story.storyid) }
                           .padding(8.dp),
                        text = "View Chapters",
                        color = MaterialTheme.colorScheme.primary
                     )
                  }

               }
            }
         }
      }
   }
}

@Composable
fun StoryScreen(
   storyId: Int,
   onChapterClick: (Int) -> Unit,
   modifier: Modifier = Modifier,
   viewModel: StoryViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   val story = viewModel.getStory(storyId)
   viewModel.loadChapters(storyId)
   val chapterList = viewModel.chapterList

   Scaffold(
      topBar = {
         PetAppBar(
            onUpClick = onUpClick,
            canNavigateBack = true,
            title = story.storyname
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding)
      ) {
         // Synopsis
         ExpandableSection(modifier = modifier, title = "Synopsis") {
            Text(
               modifier = Modifier.padding(8.dp),
               text = story.synopsis,
               color = MaterialTheme.colorScheme.onSecondaryContainer
            )
         }
         // Chapters
         Text(
            text = "Chapters",
            style = MaterialTheme.typography.headlineMedium
         )
         LazyColumn {
            items(chapterList) { chapter ->
               Row (modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onChapterClick(chapter.chapterid)},
                     horizontalArrangement = Arrangement.SpaceBetween
               ) {
                  Text(chapter.chaptername)
                  Text((chapter.wordcount).toString())
               }
            }
         }

         // Scratch Pad
         Text(
            text = "Scratch Pad",
            style = MaterialTheme.typography.headlineMedium
         )

      }
   }
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
         PetAppBar(
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

//@Composable
//fun ToDoScreen(
//   modifier: Modifier = Modifier,
//   todoViewModel: ToDoViewModel = viewModel()
//) {
//   Scaffold(
//      topBar = {
//         ToDoAppTopBar(
//            completedTasksExist = todoViewModel.completedTasksExist,
//            onDeleteCompletedTasks = todoViewModel::deleteCompletedTasks,
//            onCreateTasks = todoViewModel::createTestTasks,
//            archivedTasksExist = todoViewModel.archivedTasksExist,
//            onRestoreArchive = todoViewModel::restoreArchivedTasks
//         )
//      }
//   ) { innerPadding ->
//      Column(
//         modifier = modifier
//            .fillMaxSize()
//            .padding(innerPadding),
//      ) {
//         AddTaskInput(todoViewModel::addTask)
//         TaskList(
//            taskList = todoViewModel.taskList,
//            onDeleteTask = todoViewModel::deleteTask,
//            onArchiveTask = todoViewModel::archiveTask,
//            onToggleTaskComplete = todoViewModel::toggleTaskCompleted
//         )
//      }
//   }
//}
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun TaskList(
//   taskList: List<Task>,
//   onDeleteTask: (Task) -> Unit,
//   onArchiveTask: (Task) -> Unit,
//   onToggleTaskComplete: (Task) -> Unit
//) {
//   LazyColumn {
//      items(
//         items = taskList,
//         key = { task -> task.id }
//      ) { task ->
//         val currentTask by rememberUpdatedState(task)
//         val dismissState = rememberDismissState(
//            confirmValueChange = {
//               when (it) {
//                  DismissValue.DismissedToEnd -> {
//                     onDeleteTask(currentTask)
//                     true
//                  }
//
//                  DismissValue.DismissedToStart -> {
//                     onArchiveTask(currentTask)
//                     true
//                  }
//
//                  else -> false
//               }
//            }
//         )
//
//      }
//   }
//}
//
//@Composable
//fun TaskCard(
//   task: Task,
//   toggleCompleted: (Task) -> Unit,
//   modifier: Modifier = Modifier
//) {
//   Card(
//      modifier = modifier
//         .padding(8.dp)
//         .fillMaxWidth(),
//      colors = CardDefaults.cardColors(
//         containerColor = MaterialTheme.colorScheme.surfaceVariant
//      )
//   ) {
//      Row(
//         modifier = modifier.fillMaxWidth(),
//         verticalAlignment = Alignment.CenterVertically,
//         horizontalArrangement = Arrangement.SpaceBetween
//      ) {
//         Text(
//            text = task.body,
//            modifier = modifier.padding(start = 12.dp),
//            color = if (task.completed) Color.Gray else Color.Black
//         )
//         Checkbox(
//            checked = task.completed,
//            onCheckedChange = {
//               toggleCompleted(task)
//            }
//         )
//      }
//   }
//}
//
//
//@Composable
//fun AddTaskInput(onEnterTask: (String) -> Unit) {
//   val keyboardController = LocalSoftwareKeyboardController.current
//   var taskBody by remember { mutableStateOf("") }
//
//   OutlinedTextField(
//      modifier = Modifier
//         .fillMaxWidth()
//         .padding(6.dp),
//      value = taskBody,
//      onValueChange = { taskBody = it },
//      label = { Text("Enter task") },
//      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//      keyboardActions = KeyboardActions(
//         onDone = {
//            onEnterTask(taskBody)
//            taskBody = ""
//            keyboardController?.hide()
//         }),
//   )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppTopBar(
   completedTasksExist: Boolean,
   onDeleteCompletedTasks: () -> Unit,
   archivedTasksExist: Boolean,
   onRestoreArchive: () -> Unit,
   onCreateTasks: () -> Unit
) {
   var showDeleteTasksDialog by remember { mutableStateOf(false) }

   if (showDeleteTasksDialog) {
      DeleteTasksDialog(
         onDismiss = {
            showDeleteTasksDialog = false
         },
         onConfirm = {
            showDeleteTasksDialog = false
            onDeleteCompletedTasks()
         }
      )
   }

   TopAppBar(
      colors = topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer,
         titleContentColor = MaterialTheme.colorScheme.primary,
      ),
      title = {
         Text(text = "To-Do List")
      },
      actions = {
         IconButton(onClick = onCreateTasks) {
            Icon(
               Icons.Default.Add,
               contentDescription = "Add Tasks"
            )
         }
         IconButton(
            onClick = onRestoreArchive,
            enabled = archivedTasksExist
         ) {
            Icon(
               Icons.Default.Refresh,
               contentDescription = "Restore Archived Tasks"
            )
         }
         IconButton(
            onClick = { showDeleteTasksDialog = true },
            enabled = completedTasksExist
         ) {
            Icon(
               Icons.Default.Delete,
               contentDescription = "Delete"
            )
         }
      }
   )
}



@Preview
@Composable
fun PreviewGalaxyScreen() {
   ToDoListTheme {
      GalaxyScreen(
         onImageClick = {}
      )
   }
}

@Preview
@Composable
fun PreviewWorldScreen() {
   val world = WorldDataSource().loadWorlds()[0]
   ToDoListTheme {
      WorldScreen(
         worldId = world.worldid,
         onStoryClick = {}
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
         onChapterClick = {}
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

