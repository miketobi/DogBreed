package com.example.dogbreed.di

import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.data.repository.DogBreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindsRepository(
        repositoryImpl: DogBreedRepositoryImpl
    ): DogBreedRepository
}