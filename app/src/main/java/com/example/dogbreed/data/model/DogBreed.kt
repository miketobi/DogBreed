package com.example.dogbreed.data.model

data class DogBreed(
    val name: String,
    val imgUrl: String,
    val subBreeds: List<String>
)

fun Map<String, List<String>>.toDogBreed(imgUrl: String) = map {
    DogBreed(
        name = it.key.capitalize(),
        imgUrl = imgUrl,
        subBreeds = it.value
    )
}