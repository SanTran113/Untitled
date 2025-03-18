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
        AddStoryDialog(
            onConfirmation = { title ->
                viewModel.hideStoryDialog()
                viewModel.addStory(title)
            },
            onDismissRequest = {
                viewModel.hideStoryDialog()
            }
        )
    }

    Scaffold(
        topBar = {
            WorldTopAppBar(
                worldTitle = uiState.value.world.worldName,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            if (!uiState.value.isCabVisible) {
                FloatingActionButton(
                    onClick = { viewModel.showStoryDialog() },
                ) {
                    Icon(Icons.Filled.Add, "Add")
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldTopAppBar(
    worldTitle: String,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(worldTitle) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,"Back")
            }
        }
    )
}

@Composable
fun AddStoryDialog(
    onConfirmation: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var story by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("Story Name?") },
                value = story,
                onValueChange = { story = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(story)
                    }
                )
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    onConfirmation(story)
                }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                    onDismissRequest()
                }) {
                Text(text = "Cancel")
            }
        },
    )
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
