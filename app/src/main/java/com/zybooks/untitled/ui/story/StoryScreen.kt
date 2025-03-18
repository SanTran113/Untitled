package com.zybooks.untitled.ui.story

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.ui.world.AddStoryDialog
import com.zybooks.untitled.ui.world.WorldTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel = viewModel(
        factory = StoryViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onChapterClick: (Chapter) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isChapterDialogVisible) {
        AddChapterDialog (
            onConfirmation = { title ->
                viewModel.hideChapterDialog()
                viewModel.addChapter(title)
            },
            onDismissRequest = {
                viewModel.hideChapterDialog()
            }
        )
    }

    Scaffold(
        topBar = {
            StoryAppBar(
                storyTitle = uiState.value.story.storyName,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            if (!uiState.value.isCabVisible) {
                FloatingActionButton(
                    onClick = { viewModel.showChapterDialog() },
                ) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }
    ) { innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Synopsis
            Synopsis(
                modifier = modifier.padding(innerPadding),
                synopsis = uiState.value.story.synopsis,
            )

            ChapterSection(
                chapterList = uiState.value.chapList,
                inSelectionMode = uiState.value.isCabVisible,
                selectedChapters = uiState.value.selectedChapters,
                onChapterClick = onChapterClick,
                onSelectChapter = { viewModel.selectChapter(it)},
                modifier = modifier.padding(innerPadding)
            )

            ScracthPad(
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Synopsis(
    modifier: Modifier = Modifier,
    synopsis: String
) {
    ExpandableSection(modifier = modifier, title = "SYNOPSIS"
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = synopsis,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ChapterSection(
    chapterList: List<Chapter>,
    onChapterClick: (Chapter) -> Unit,
    modifier: Modifier = Modifier,
    onSelectChapter: (Chapter) -> Unit = { },
    inSelectionMode: Boolean = false,
    selectedChapters: Set<Chapter> = emptySet()
) {
    Box(modifier = modifier) {
        Column (
            modifier = Modifier
                .padding(20.dp)
        ){
            Text(
                text = "CHAPTERS",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth().width(1.dp)
                    .padding(vertical = 3.dp),
                color = Color.Black
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(0.dp),
            ) {
                items(chapterList, key = { it.chapterId }) { chapter ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (inSelectionMode) {
                                    onSelectChapter(chapter)
                                } else {
                                    onChapterClick(chapter)
                                }
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = chapter.chapterName,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = (chapter.wordCount).toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScracthPad(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column {
            Text(
                text = "SCRATCH PAD",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryAppBar(
    storyTitle: String,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(storyTitle) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,"Back")
            }
        }
    )
}

@Composable
fun AddChapterDialog(
    onConfirmation: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var chapter by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("Chapter Name?") },
                value = chapter,
                onValueChange = { chapter = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(chapter)
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
                    onConfirmation(chapter)
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
