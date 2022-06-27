package com.example.dogbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("message")
    val data: T,
    val status: String
)