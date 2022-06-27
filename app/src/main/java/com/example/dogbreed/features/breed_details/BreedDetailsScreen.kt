package com.example.dogbreed.features.breed_details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.dogbreed.data.model.BreedImage
import com.example.dogbreed.ui.components.AppBar

@Composable
fun BreedDetailsScreen(
    navController: NavHostController,
    viewModel: BreedDetailsViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    BreedDetailsContent(viewState = viewState) {
        navController.popBackStack()
    }
}

@Composable
fun BreedDetailsContent(
    viewState: BreedDetailsViewState,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(topBar = {
        AppBar(title = viewState.appBarTitle) {
            onBackPressed()
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (viewState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (viewState.images.isNotEmpty()) {
                LazyVerticalGrid(state = rememberLazyListState(), cells = GridCells.Fixed(2)) {
                    items(viewState.images) {
                        Box(modifier = Modifier.padding(20.dp)) {
                            Image(
                                painter = rememberImagePainter(data = it.imgUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .size(150.dp)
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No breeds, please check back")
                }
            }

            viewState.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview
@Composable
fun PreviewBreedDetailsContentLoadingState() {
    BreedDetailsContent(
        viewState = BreedDetailsViewState(isLoading = true)
    ) {}
}

@Preview
@Composable
fun PreviewBreedDetailsContent() {
    BreedDetailsContent(
        viewState = BreedDetailsViewState(
            images = listOf(
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10822.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10822.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_1023.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10263.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10715.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10822.jpg"),
                BreedImage("https://images.dog.ceo/breeds/hound-afghan/n02088094_10832.jpg")
            )
        )
    ) {

    }
}