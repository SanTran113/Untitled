package com.zybooks.untitled.ui.story

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.ui.BottomButton
import com.zybooks.untitled.ui.ExpandableSection
import com.zybooks.untitled.ui.UntitledAppBar


@Composable
fun StoryScreen(
    storyId: Int,
    onChapterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoryViewModel = viewModel(),
    onChapterButtonClick: () -> Unit = { },
    onUpClick: () -> Unit = { }
) {
    val story = viewModel.getStory(storyId)
    viewModel.loadChapters(storyId)
    val chapterList = viewModel.chapterList

    Scaffold(
        topBar = {
            UntitledAppBar(
                onUpClick = onUpClick,
                canNavigateBack = true,
                title = story.storyname
            )
        },

        floatingActionButton = {
            BottomButton("Chapter", onChapterButtonClick)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Synopsis
            ExpandableSection(modifier = modifier, title = "SYNOPSIS"
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = story.synopsis,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.SemiBold
                )
            }
            // Chapters
            Box {
                Column (
                    modifier = Modifier
                        .padding(20.dp)
                ){
                    Text(
                        text = "CHAPTERS",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )

                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth().width(1.dp)
                            .padding(vertical = 3.dp)
                    )

                    LazyColumn (
                        contentPadding = PaddingValues(vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        items(chapterList) { chapter ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onChapterClick(chapter.chapterid) },
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = chapter.chaptername,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = (chapter.wordcount).toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
            // Scratch Pad
            Box {
                Column {
                    Text(
                        text = "SCRATCH PAD",
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}
