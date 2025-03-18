package com.zybooks.untitled.ui.galaxy

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.World
import com.zybooks.untitled.ui.components.AddDialog
import com.zybooks.untitled.ui.components.CabAppBar
import com.zybooks.untitled.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalaxyScreen(
    modifier: Modifier = Modifier,
    viewModel: GalaxyViewModel = viewModel(
        factory = GalaxyViewModel.Factory
    ),
    onWorldClick: (World) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isWorldDialogVisible) {
        AddDialog(
            onConfirmation = { title ->
                viewModel.hideStoryDialog()
                viewModel.addWorld(title)
            },
            onDismissRequest = {
                viewModel.hideStoryDialog()
            },
            text = "World Name?"
        )
    }

    Scaffold(
        topBar = {
            if (uiState.value.isCabVisible) {
                CabAppBar(
                    onDeleteClick = { viewModel.deleteSelectedWorld() },
                    onUpClick = { viewModel.hideCab() }
                )
            } else {
                TopAppBar(
                    title = { Text("Galaxy") }
                )
            }
        },
        floatingActionButton = {
            if (!uiState.value.isCabVisible) {
                FloatingActionButton(
                    onClick = { viewModel.showWorldDialog() },
                ) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }
    ) { innerPadding ->
        GalaxyGrid(
            worldList = uiState.value.worldList,
            inSelectionMode = uiState.value.isCabVisible,
            selectedWorlds = uiState.value.selectedWorlds,
            onWorldClick = onWorldClick,
            onSelectWorld = { viewModel.selectWorld(it) },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalaxyGrid(
    worldList: List<World>,
    onWorldClick: (World) -> Unit,
    modifier: Modifier = Modifier,
    onSelectWorld: (World) -> Unit = { },
    inSelectionMode: Boolean = false,
    selectedWorlds: Set<World> = emptySet()
) {
    val haptics = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(20.dp),
        modifier = modifier
    ) {
        items(worldList, key = { it.worldId }) { world ->
            Card(
                colors = CardDefaults.cardColors(
                    Color.LightGray
                ),
                shape = RoundedCornerShape(100),
                modifier = Modifier
                    .animateItem()
                    .height(180.dp)
                    .padding(10.dp)
                    .combinedClickable(
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSelectWorld(world)
                        },
                        onClick = {
                            if (inSelectionMode) {
                                onSelectWorld(world)
                            } else {
                                onWorldClick(world)
                            }
                        },
                        onClickLabel = world.worldName,
                        onLongClickLabel = "Select for deleting"
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (selectedWorlds.contains(world)) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Check",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                        )
                    }
                    Text(
                        text = world.worldName,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

