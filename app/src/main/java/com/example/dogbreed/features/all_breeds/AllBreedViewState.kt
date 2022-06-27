package com.example.dogbreed.features.all_breeds

import com.example.dogbreed.data.model.DogBreed

data class AllBreedViewState(
    val breeds: List<DogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)