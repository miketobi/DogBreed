package com.example.dogbreed.features.breed_details

import com.example.dogbreed.data.model.BreedImage

data class BreedDetailsViewState(
    val isLoading: Boolean = false,
    val appBarTitle: String = "",
    val images: List<BreedImage> = emptyList(),
    val error: String? = null
)