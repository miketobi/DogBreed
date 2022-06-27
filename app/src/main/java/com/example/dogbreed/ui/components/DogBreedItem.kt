package com.example.dogbreed.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun DogBreedItem(
    breedName: String,
    subBreeds: List<String>,
    imageUrl: String,
    onBreedClicked: (String) -> Unit,
    onChipClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(0.2.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .clickable { onBreedClicked(breedName) },
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(Modifier.padding(20.dp)) {
            Text(
                text = breedName,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (subBreeds.isNotEmpty()) {
                ChipVerticalGrid(
                    spacing = 10.dp,
                ) {
                    subBreeds.forEach {
                        Chip(text = it) { chipText ->
                            onChipClicked(chipText)
                        }
                    }
                }
            } else {
                Text(
                    text = "No subbreeds listed at the moment",
                    style = MaterialTheme.typography.caption.copy(
                        color = Color.Gray,
                        letterSpacing = 0.1.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDogBreedItem() {
    Box(Modifier.padding(20.dp)) {
        DogBreedItem(
            breedName = "Hound",
            subBreeds =
            listOf(
                "hound 1",
                "hound 2",
                "hound 3",
                "hound 4",
                "hound 5"
            ),
            imageUrl = "https://images.dog.ceo/breeds/hound-afghan/n02088094_10822.jpg",
            onBreedClicked = {},
            onChipClicked = {}
        )
    }
}