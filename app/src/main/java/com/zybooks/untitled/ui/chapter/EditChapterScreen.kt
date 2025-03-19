package com.zybooks.untitled.ui.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.Chapter
import com.zybooks.untitled.ui.components.BottomButton
import com.zybooks.untitled.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditChapterScreen(
    modifier: Modifier = Modifier,
    viewModel: EditChapterViewModel = viewModel(
        factory = EditChapterViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val chapter = viewModel.chapter

    Scaffold (
        topBar = {
            TopBar(
                title = chapter.chapterName,
                onUpClick = onUpClick,

                )
        },
        floatingActionButton = {
            BottomButton(
                onClick = {
                    viewModel.updateChapter()
                    onSaveClick()
                },
                text = "Save",
                icon = Icons.Filled.Done
            )
        }
    ) { innerPadding ->
        EditChapterEntry(
            modifier = modifier.padding(innerPadding),
            chapter = chapter,
            onEditChapterBody = { viewModel.changeChap(it)}
        )
    }
}

@Composable
fun EditChapterEntry (
    modifier: Modifier = Modifier,
    chapter: Chapter,
    onEditChapterBody: (Chapter) -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = chapter.chapterBody,
        onValueChange = { onEditChapterBody(chapter.copy(chapterBody = it)) },
        singleLine = false,
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.LightGray),
        colors = OutlinedTextFieldDefaults.colors( unfocusedContainerColor = Color(0xFFDEDEDE) )
    )
}
