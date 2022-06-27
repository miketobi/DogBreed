package com.example.dogbreed.features.all_breeds

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dogbreed.data.model.DogBreed
import com.example.dogbreed.ui.components.AppBar
import com.example.dogbreed.ui.components.DogBreedItem
import com.example.dogbreed.util.Route

@Composable
fun AllBreedScreen(
    navController: NavHostController,
    viewModel: AllBreedsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val viewState by viewModel.viewState.collectAsState()

    AllBreedContent(
        viewState = viewState,
        onItemClicked = { breedName, subBreedName ->
            navController.navigate("${Route.breed_details_route}/$breedName/$subBreedName")
        },
        onError = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun AllBreedContent(
    viewState: AllBreedViewState,
    onItemClicked: (String, String?) -> Unit,
    onError: (String) -> Unit
) {
    Scaffold(topBar = {
        AppBar(title = "All Breeds", icon = null)
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (viewState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (viewState.breeds.isNotEmpty()) {
                LazyColumn(
                    state = rememberLazyListState()
                ) {
                    items(viewState.breeds) { breed ->
                        Box(modifier = Modifier.padding(20.dp)) {
                            DogBreedItem(
                                breedName = breed.name,
                                subBreeds = breed.subBreeds,
                                imageUrl = breed.imgUrl,
                                onBreedClicked = {
                                    onItemClicked(breed.name, null)
                                },
                                onChipClicked = { subBreedName ->
                                    onItemClicked(
                                        breed.name,
                                        subBreedName
                                    )
                                })
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
                onError(it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewAllBreedContentLoadingState() {
    AllBreedContent(AllBreedViewState(isLoading = true), { _, _ -> }) {}
}

@Preview
@Composable
fun PreviewAllBreedEmptyState() {
    AllBreedContent(AllBreedViewState(), { _, _ -> }) {}
}

@Preview
@Composable
fun PreviewAllBreed() {
    AllBreedContent(
        AllBreedViewState(
            breeds = listOf(
                DogBreed(
                    name = "Hound",
                    subBreeds = listOf(
                        "hound 1",
                        "hound 2",
                        "houndx",
                        "hounds",
                    ),
                    imgUrl = "https://images.dog.ceo/breeds/hound-basset/n02088238_9267.jpg"
                )
            )
        ), { _, _ -> }) {}
}