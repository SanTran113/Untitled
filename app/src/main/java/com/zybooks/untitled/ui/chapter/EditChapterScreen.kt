package com.zybooks.untitled.ui.chapter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EditChapterScreen(
    modifier: Modifier = Modifier,
    viewModel: EditChapterViewModel = viewModel(
        factory = EditChapterViewModel.Factory
    ),
    onUpClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {

}