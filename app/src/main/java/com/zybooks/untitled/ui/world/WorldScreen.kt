package com.zybooks.untitled.ui.world

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.ui.BottomButton
import com.zybooks.untitled.ui.ExpandableSection
import com.zybooks.untitled.ui.UntitledAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldScreen(
    worldId: Int,
    onStoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorldViewModel = viewModel(
        factory = WorldViewModel.Factory
    ),
    onStoryButtonClick: () -> Unit,
    onUpClick: () -> Unit = { }
) {
    val world = viewModel.getWorld(worldId)
    viewModel.loadStories(worldId)
    val storyList = viewModel.storyList

    Scaffold(
        topBar = {
            UntitledAppBar(
                onUpClick = onUpClick,
                title = world.worldname,
                canNavigateBack = true,
            )
        },
        floatingActionButton = {
            BottomButton("Story", onStoryButtonClick)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(storyList) { story ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
}
