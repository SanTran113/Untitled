package com.zybooks.untitled.ui.story

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.data.Story
import com.zybooks.untitled.ui.components.AddDialog
import com.zybooks.untitled.ui.components.BottomButton
import com.zybooks.untitled.ui.components.ExpandableSection
import com.zybooks.untitled.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel = viewModel(
        factory = StoryViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onChapterClick: (Chapter) -> Unit = {},
    onScratchPadClick: (Long) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isChapterDialogVisible) {
        AddDialog (
            onConfirmation = { title ->
                viewModel.hideChapterDialog()
                viewModel.addChapter(title)
            },
            onDismissRequest = {
                viewModel.hideChapterDialog()
            },
            text = "Chapter Name?"
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = uiState.value.story.storyName,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            if (!uiState.value.isCabVisible) {
                BottomButton(
                    onClick = { viewModel.showChapterDialog() },
                    text = "CHAPTER",
                    icon = Icons.Filled.Add
                )
            }
        }
    ) { innerPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 7.dp)
        ){
            // Synopsis
            Synopsis(
                modifier = modifier,
                synopsis = uiState.value.story.synopsis,
                onSynopsisClick = { newSynopsis -> viewModel.updateSynopsis(newSynopsis)},
                onEditStory = { viewModel.updateStory(it)},
                story = uiState.value.story
            )

            ChapterSection(
                chapterList = uiState.value.chapList,
                inSelectionMode = uiState.value.isCabVisible,
                selectedChapters = uiState.value.selectedChapters,
                onChapterClick = onChapterClick,
                onSelectChapter = { viewModel.selectChapter(it)},
                modifier = modifier.padding(top = 7.dp)
            )

            ScracthPad(
                modifier = modifier.padding(top = 7.dp),
                onScratchPadClick = { onScratchPadClick(uiState.value.story.storyId) },
                story = uiState.value.story,
                onEditStory = { viewModel.updateStory(it)},
            )
        }
    }
}

@Composable
fun Synopsis(
    modifier: Modifier = Modifier,
    synopsis: String,
    onSynopsisClick: (String) -> Unit,
    onEditStory: (Story) -> Unit,
    story: Story
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedSynopsis by remember { mutableStateOf(synopsis) }

    ExpandableSection(modifier = modifier, title = "SYNOPSIS") {
        Text(
            text = synopsis,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    editedSynopsis = synopsis
                    isEditing = true
                },
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold
        )

        if (isEditing) {
            AlertDialog(
                onDismissRequest = { isEditing = false },
                title = { Text("Edit Synopsis") },
                text = {
                    TextField(
                        value = editedSynopsis,
                        onValueChange = { editedSynopsis = it },
                        singleLine = false
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onEditStory(story.copy(synopsis = editedSynopsis))
                            onSynopsisClick(editedSynopsis)
                            isEditing = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { isEditing = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
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
                .padding(30.dp)
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
                contentPadding = PaddingValues(vertical = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
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
    onScratchPadClick: (Long) -> Unit = {},
    story: Story,
    onEditStory: (Story) -> Unit,
) {
    var isEditing by remember { mutableStateOf(false) }
    var scratchPadText by remember { mutableStateOf(story.scratchPad) }
    val focusManager = LocalFocusManager.current
    val shape = RoundedCornerShape(20)

    Box(modifier = modifier
    ) {
        Column (
            modifier = Modifier.padding(30.dp)
        ){
            Text(
                text = "SCRATCH PAD",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.headlineSmall
            )

            if (isEditing) {
                TextField(
                    value = scratchPadText,
                    onValueChange = { scratchPadText = it },
                    singleLine = false,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onEditStory(story.copy(scratchPad = scratchPadText))
                            isEditing = false
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        isEditing = false
                        onEditStory(story.copy(scratchPad = scratchPadText))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .border(BorderStroke(1.dp, Color.Black), shape)
                ) {
                    Text(
                        text = "Save",
                        color = Color.DarkGray
                    )
                }
            } else {
                Text (
                    text = scratchPadText,
                    modifier = Modifier
                        .clickable { isEditing = true }
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .fillMaxWidth()
                        .height(50.dp)
                )
            }
        }
    }
}

