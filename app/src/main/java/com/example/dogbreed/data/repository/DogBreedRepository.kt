package com.example.dogbreed.data.repository

import com.example.dogbreed.data.model.BreedImage
import com.example.dogbreed.data.model.DogBreed
import com.example.dogbreed.util.Resource
import kotlinx.coroutines.flow.Flow

interface DogBreedRepository {

    suspend fun getBreedStream(): Flow<Resource<DogBreed>>

    suspend fun getBreedImages(breedName: String): Resource<List<BreedImage>>

    suspend fun getSubBreedImages(
        breedName: String,
        subBreedName: String
    ): Resource<List<BreedImage>>
}