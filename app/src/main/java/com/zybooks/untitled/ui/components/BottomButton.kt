package com.zybooks.untitled.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector
) {
    val shape = RoundedCornerShape(50)

    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(icon, "BottomButton") },
        text = { Text(
            text = text,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        ) },
        shape = shape,
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier
            .padding(8.dp)
            .border(BorderStroke(2.dp, Color.Black), shape)
    )
}