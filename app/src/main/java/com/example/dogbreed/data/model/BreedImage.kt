package com.example.dogbreed.data.model

import com.example.dogbreed.data.remote.model.GenericResponse

data class BreedImage(
    val imgUrl: String
)

fun GenericResponse<List<String>>.toBreedImage() = data.map { BreedImage(it) }