package com.example.dogbreed.data.repository

import com.example.dogbreed.data.model.*
import com.example.dogbreed.data.remote.api.DogBreedApi
import com.example.dogbreed.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher = IO,
    private val dogBreedApi: DogBreedApi
) : DogBreedRepository {
    override suspend fun getBreedStream(): Flow<Resource<DogBreed>> {
        return flow {
            try {
                val breedList = dogBreedApi.getAllBreeds()

                breedList.data.forEach {
                    val breedRandomImg = dogBreedApi.getRandomBreedImage(it.key)

                    val emitBreed = DogBreed(
                        name = it.key.capitalize(),
                        breedRandomImg.data,
                        it.value
                    )

                    //don't wait for all items to be fetched, start emitting each data
                    emit(Resource.Success(emitBreed))
                }

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.response()?.errorBody()?.string()))
            }
        }
            .flowOn(ioDispatcher)
            .onStart { emit(Resource.Loading(true)) }
    }

    override suspend fun getBreedImages(breedName: String): Resource<List<BreedImage>> {
        return withContext(ioDispatcher) {
            try {
                val response = dogBreedApi.getBreedImages(breedName.lowercase())
                Resource.Success(response.toBreedImage())
            } catch (e: IOException) {
                e.printStackTrace()
                Resource.Error(message = e.message)
            } catch (e: HttpException) {
                e.printStackTrace()
                Resource.Error(message = e.response()?.errorBody()?.string())
            }
        }
    }

    override suspend fun getSubBreedImages(
        breedName: String,
        subBreedName: String
    ): Resource<List<BreedImage>> {
        return withContext(ioDispatcher) {
            try {
                val response = dogBreedApi.getSubBreedImages(breedName.lowercase(), subBreedName)
                Resource.Success(response.toBreedImage())
            } catch (e: IOException) {
                e.printStackTrace()
                Resource.Error(message = e.message)
            } catch (e: HttpException) {
                e.printStackTrace()
                Resource.Error(message = e.response()?.errorBody()?.string())
            }
        }
    }
}


