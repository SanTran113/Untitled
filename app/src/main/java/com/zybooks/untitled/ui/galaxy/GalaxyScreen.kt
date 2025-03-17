package com.zybooks.untitled.ui.galaxy

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.untitled.data.World
import com.zybooks.untitled.ui.BottomButton
import com.zybooks.untitled.ui.Routes
import com.zybooks.untitled.ui.world.WorldViewModel

@Composable
fun GalaxyScreen(
    onImageClick: (World) -> Unit,
    modifier: Modifier = Modifier,
    galaxyViewModel: GalaxyViewModel = viewModel(
        factory = GalaxyViewModel.Factory
    ),
    worldViewModel: WorldViewModel = viewModel(
        factory = WorldViewModel.Factory
    ),
    onWorldButtonClick: (World) -> Unit = {}
) {
    val uiState = galaxyViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.isSubjectDialogVisible) {
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
            BottomButton("World", onWorldButtonClick)
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

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = modifier.padding(innerPadding)
            ) {
                items(viewModel.galaxyDataSource) { galaxy ->
                    Image(
                        painter = painterResource(id = galaxy.imageId),
                        contentDescription = "Part of ${galaxy.galaxyname}",
                        modifier = Modifier.clickable(
                            onClick = { onImageClick(galaxy) },
                            onClickLabel = "Select the world"
                        )
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