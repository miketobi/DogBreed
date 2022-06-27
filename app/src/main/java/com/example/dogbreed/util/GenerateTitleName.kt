package com.example.dogbreed.util

import javax.inject.Inject

class GenerateTitleName @Inject constructor() {
    fun getTitle(breedName: String, subBreedName: String? = null): String {
        return "All " +
                (if (subBreedName.isNullOrEmpty())
                    breedName
                else
                    "$breedName $subBreedName") +
                " Images"
    }
}