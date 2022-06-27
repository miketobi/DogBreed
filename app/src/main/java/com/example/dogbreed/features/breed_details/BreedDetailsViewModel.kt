package com.example.dogbreed.features.breed_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreed.util.GenerateTitleName
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.util.NavArgs
import com.example.dogbreed.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val repository: DogBreedRepository,
    private val savedStateHandle: SavedStateHandle,
    private val generateTitleName: GenerateTitleName
) : ViewModel() {

    var viewState = MutableStateFlow(BreedDetailsViewState())

    init {
        val breedName = requireNotNull(savedStateHandle.get<String>(NavArgs.breed_name))
        var subBreedName = savedStateHandle.get<String>(NavArgs.sub_breed_name)
        subBreedName = if (subBreedName == "null") null else subBreedName

        viewState.value =
            viewState.value.copy(appBarTitle = generateTitleName.getTitle(breedName, subBreedName))

        fetchImages(breedName, subBreedName)
    }

    private fun fetchImages(breedName: String, subBreedName: String?) {
        viewState.value = viewState.value.copy(isLoading = true)

        viewModelScope.launch {
            val response = if (subBreedName.isNullOrEmpty()) repository.getBreedImages(breedName)
            else repository.getSubBreedImages(breedName, subBreedName)

            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        viewState.value = viewState.value.copy(
                            images = it,
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    viewState.value = viewState.value.copy(
                        error = response.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}