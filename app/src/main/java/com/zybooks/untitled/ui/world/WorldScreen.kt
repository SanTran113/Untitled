package com.zybooks.untitled.ui.world

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.Story

@Composable
fun WorldScreen(
    modifier: Modifier = Modifier,
    viewModel: WorldViewModel = viewModel(
        factory = WorldViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onStoryClick: (Story) -> Unit = {}
) {

}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun WorldScreen(
//    worldId: Int,
//    onStoryClick: (Int) -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: WorldViewModel = viewModel(
//        factory = WorldViewModel.Factory
//    ),
//    onStoryButtonClick: () -> Unit,
//    onUpClick: () -> Unit = { }
//) {
//    val world = viewModel.getWorld(worldId)
//    viewModel.loadStories(worldId)
//    val storyList = viewModel.storyList
//
//    Scaffold(
//        topBar = {
//            UntitledAppBar(
//                onUpClick = onUpClick,
//                title = world.worldname,
//                canNavigateBack = true,
//            )
//        },
//        floatingActionButton = {
//            BottomButton("Story", onStoryButtonClick)
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = modifier.padding(innerPadding)
//        ) {
//            LazyColumn {
//                items(storyList) { story ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 5.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        ExpandableSection(modifier = modifier, title = story.storyname) {
//                            Column {
//                                Text(
//                                    modifier = Modifier.padding(8.dp),
//                                    text = story.synopsis,
//                                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                                )
//                                Text(
//                                    modifier = Modifier
//                                        .clickable { onStoryClick(story.storyid) }
//                                        .padding(8.dp),
//                                    text = "View Chapters",
//                                    color = MaterialTheme.colorScheme.primary
//                                )
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
