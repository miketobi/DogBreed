package com.example.dogbreed.features.all_breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBreedsViewModel @Inject constructor(
    private val repository: DogBreedRepository
) : ViewModel() {

    var viewState = MutableStateFlow(AllBreedViewState())
        private set

    init {
        fetchAllBreeds()
    }

    private fun fetchAllBreeds() {
        viewModelScope.launch {
            repository.getBreedStream().collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { data ->
                            viewState.value = viewState.value.copy(
                                breeds = viewState.value.breeds.plus(data),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        viewState.value = viewState.value.copy(
                            isLoading = it.isLoading
                        )
                    }
                    is Resource.Error -> {
                        viewState.value =
                            viewState.value.copy(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }
}