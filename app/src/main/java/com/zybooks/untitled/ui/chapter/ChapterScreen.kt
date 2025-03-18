package com.zybooks.untitled.ui.chapter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChapterScreen(
    modifier: Modifier = Modifier,
    viewModel: ChapterViewModel = viewModel(
        factory = ChapterViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onEditClick: (Long) -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            ChapterTopAppBar(
                chapterTitle = uiState.value.chapter.chapterName,
                onUpClick = onUpClick
            )
        },
        floatingActionButton = {
            IconButton(onClick = { onEditClick(uiState.value.chapter.chapterId) }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit",
                )
            }
        }
    ) { innerPadding ->
        ChapterBody (
            modifier = modifier.padding(innerPadding),
            chapterBody = uiState.value.chapter.chapterBody
        )

    }
}

@Composable
fun ChapterBody(
    modifier: Modifier = Modifier,
    chapterBody: String
) {
    Text(
        modifier = modifier,
        text = chapterBody
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterTopAppBar(
    chapterTitle: String,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(chapterTitle) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,"Back")
            }
        }
    )
}


