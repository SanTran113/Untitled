package com.zybooks.untitled.ui.galaxy

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.World
//import com.zybooks.untitled.ui.BottomButton

@Composable
fun GalaxyScreen(
    onWorldClick: (World) -> Unit,
    modifier: Modifier = Modifier,
    galaxyViewModel: GalaxyViewModel = viewModel(
        factory = GalaxyViewModel.Factory
    )
) {
    val uiState = galaxyViewModel.uiState.collectAsStateWithLifecycle()

    Log.d("GalaxyScreen", "World list: ${uiState.value.worldList}")

    if (uiState.value.isWorldDialogVisible) {
        AddWorldDialog(
            onConfirmation = { worldName ->
                galaxyViewModel.hideWorldDialog()
                galaxyViewModel.addWorld(worldName)
            },
            onDismissRequest = {
                galaxyViewModel.hideWorldDialog()
            }
        )
    }

    Scaffold(
        floatingActionButton = {
//            BottomButton("World", onClick = { galaxyViewModel.showWorldDialog() })
        }
    ) { innerPadding ->
        Column {
            Text(
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth(),
                text = "WELCOME TO YOUR GALAXY",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )

            GalaxyGrid(
                worldList = uiState.value.worldList,
                onWorldClick = onWorldClick,
                modifier = modifier.padding(innerPadding),
                onSelectWorld = { galaxyViewModel.selectWorld(it)},
                inSelectionMode = uiState.value.isCabVisible,
                selectedWorld = uiState.value.selectedWorlds

            )
        }
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
    selectedWorld: Set<World> = emptySet()
) {
    val haptics = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        items(worldList, key = { it.worldId }) { world ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier
                    .animateItem()
                    .height(100.dp)
                    .padding(4.dp)
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
                    if (selectedWorld.contains(world)) {
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
                        color = Color.White
                    )
                }
            }
        }
    }
}
@Composable
fun AddWorldDialog(
    onConfirmation: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var world by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            TextField(
                label = { Text("World Name?") },
                value = world,
                onValueChange = { world = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirmation(world)
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
                    onConfirmation(world)
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