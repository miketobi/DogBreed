package com.example.dogbreed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.primary,
    onChipClicked: (String) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(color.copy(0.13f))
            .clickable { onChipClicked(text) }
            .padding(horizontal = 15.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2.copy(
                color = color
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewChip() {
    Box(Modifier.padding(20.dp)) {
        Chip(text = "shepherd") {}
    }
}
