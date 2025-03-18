package com.zybooks.untitled.ui.chapter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

}