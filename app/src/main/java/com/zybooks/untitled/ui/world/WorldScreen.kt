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
import com.zybooks.untitled.ui.components.AddDialog
import com.zybooks.untitled.ui.components.BottomButton
import com.zybooks.untitled.ui.components.ExpandableSection
import com.zybooks.untitled.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldScreen(
    modifier: Modifier = Modifier,
    viewModel: WorldViewModel = viewModel(
        factory = WorldViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onStoryClick: (Story) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isWorldDialogVisible) {
        AddDialog(
            onConfirmation = { title ->
                viewModel.hideStoryDialog()
                viewModel.addStory(title)
            },
            onDismissRequest = {
                viewModel.hideStoryDialog()
            },
            text = "Story Name?"
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.value.world.worldName,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            if (!uiState.value.isCabVisible) {
                BottomButton(
                    onClick = { viewModel.showStoryDialog() },
                    text = "Story"
                )
            }
        }
    ) { innerPadding ->
        StoryDropDowns(
            storyList = uiState.value.storyList,
            inSelectionMode = uiState.value.isCabVisible,
            selectedStories = uiState.value.selectedStories,
            onStoryClick = onStoryClick,
            onSelectStory = { viewModel.selectStory(it)},
            modifier = modifier.padding(innerPadding)
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryDropDowns(
    storyList: List<Story>,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
    onSelectStory: (Story) -> Unit = { },
    inSelectionMode: Boolean = false,
    selectedStories: Set<Story> = emptySet()
) {
    val haptics = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        items(storyList, key = { it.storyId }) { story ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ExpandableSection(modifier = modifier, title = story.storyName) {
                    Column {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = story.synopsis,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    if (inSelectionMode) {
                                        onSelectStory(story)
                                    } else {
                                        onStoryClick(story)
                                    }
                                },
                            text = "View Chapters",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }
        }
    }
}

