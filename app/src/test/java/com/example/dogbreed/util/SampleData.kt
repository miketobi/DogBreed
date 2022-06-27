package com.example.dogbreed.util

import com.example.dogbreed.data.model.BreedImage
import com.example.dogbreed.data.model.DogBreed

object SampleData {
    val sampleBreeds = listOf(
        DogBreed("African", "img.png", emptyList()),
        DogBreed("Bulldog", "img.png", listOf("boston", "english", "french")),
        DogBreed("Bullterrier", "img.png", listOf("staffordshire")),
        DogBreed(
            "Hound", "img.png", listOf(
                "afghan",
                "basset",
                "blood",
                "english",
                "ibizan",
                "plott",
                "walker"
            )
        )
    )

    val breedImages = listOf(
        BreedImage("img1.png"),
        BreedImage("img2.png"),
        BreedImage("img3.png")
    )

}