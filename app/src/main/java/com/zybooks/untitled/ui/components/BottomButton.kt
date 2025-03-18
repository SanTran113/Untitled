package com.zybooks.untitled.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomButton(
    onClick: () -> Unit,
    text: String
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, "BottomButton") },
        text = { Text(
            text = text,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        ) },
        shape = RoundedCornerShape(50)
    )
}