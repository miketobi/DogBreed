package com.example.dogbreed.data.remote.api

import com.example.dogbreed.data.remote.model.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): GenericResponse<Map<String, List<String>>>

    @GET("breed/{breedName}/images/random")
    suspend fun getRandomBreedImage(@Path("breedName") breedName: String): GenericResponse<String>

    @GET("breed/{breedName}/images")
    suspend fun getBreedImages(@Path("breedName") breedName: String): GenericResponse<List<String>>

    @GET("breed/{breedName}/{subBreedName}/images")
    suspend fun getSubBreedImages(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String
    ): GenericResponse<List<String>>
}