package com.example.dogbreed

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.dogbreed.util.GenerateTitleName
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.features.breed_details.BreedDetailsViewModel
import com.example.dogbreed.features.breed_details.BreedDetailsViewState
import com.example.dogbreed.util.NavArgs
import com.example.dogbreed.util.Resource
import com.example.dogbreed.util.SampleData.breedImages
import com.example.dogbreed.util.SampleData.sampleBreeds
import com.example.dogbreed.util.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BreedDetailsViewModelTest {
    @get:Rule
    var testDispatcher = TestCoroutineRule()

    private val repository = mockk<DogBreedRepository>()
    private lateinit var viewModel: BreedDetailsViewModel
    private val generateTitleName = GenerateTitleName()

    @Before
    fun setup() {
        coEvery { repository.getBreedImages(any()) } returns Resource.Success(breedImages)
        viewModel = BreedDetailsViewModel(
            repository = repository,
            savedStateHandle = SavedStateHandle(mapOf(NavArgs.breed_name to sampleBreeds[0].name)),
            generateTitleName = generateTitleName
        )
    }

    private fun setupWithSubBreedArgumentPassed(shouldReturnError: Boolean = false) {
        coEvery { repository.getSubBreedImages(any(), any()) } returns
                if (shouldReturnError)
                    Resource.Error("something went wrong")
                else
                    Resource.Success(breedImages)

        viewModel = BreedDetailsViewModel(
            repository = repository,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    NavArgs.breed_name to sampleBreeds[1].name,
                    NavArgs.sub_breed_name to sampleBreeds[1].subBreeds[0]
                )
            ),
            generateTitleName = generateTitleName
        )
    }

    private fun setupWithError() {
        coEvery { repository.getBreedImages(any()) } returns Resource.Error("something went wrong")
        viewModel = BreedDetailsViewModel(
            repository = repository,
            savedStateHandle = SavedStateHandle(mapOf(NavArgs.breed_name to sampleBreeds[0].name)),
            generateTitleName = generateTitleName
        )
    }

    @Test
    fun `assert that when viewModel is launched network reuest is made`() = runBlocking {
        testDispatcher.pauseDispatcher()

        //reinitialize viewModel to get loading status while dispatcher is paused
        setup()

        viewModel.viewState.test {
            assertEquals(
                BreedDetailsViewState(
                    isLoading = true,
                    appBarTitle = "All African Images"
                ), expectItem()
            )
        }

        testDispatcher.resumeDispatcher()

        viewModel.viewState.test {
            assertEquals(
                BreedDetailsViewState(
                    isLoading = false,
                    appBarTitle = "All African Images",
                    images = breedImages
                ), expectItem()
            )
        }

        //verify no more interactions
        coVerify { repository.getBreedImages(any()) wasNot Called }
        coVerify { repository.getSubBreedImages(any(), any()) wasNot Called }
    }

    @Test
    fun `assert that when only breed name is passed as argument and network is successful, the viewState is set correctly`() =
        runBlocking {
            viewModel.viewState.test {
                coVerify { repository.getBreedImages(sampleBreeds[0].name) }

                assertEquals(
                    BreedDetailsViewState(
                        images = breedImages,
                        isLoading = false,
                        appBarTitle = "All African Images"
                    ),
                    expectItem()
                )
                //verify no more interactions
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify { repository.getSubBreedImages(any(), any()) wasNot Called }
            }
        }

    @Test
    fun `assert that when only breed name is passed as argument and network call fails, the viewState is set correctly`() =
        runBlocking {
            //re-initialize viewmodel
            setupWithError()

            viewModel.viewState.test {
                coVerify { repository.getBreedImages(sampleBreeds[0].name) }

                assertEquals(
                    BreedDetailsViewState(
                        isLoading = false,
                        appBarTitle = "All African Images",
                        error = "something went wrong"
                    ),
                    expectItem()
                )
                //verify no more interactions
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify { repository.getSubBreedImages(any(), any()) wasNot Called }
            }
        }

    @Test
    fun `assert that when breed name and subBreed name argument is passed, the right network call is made and the viewState is set correctly`() =
        runBlocking {
            //re-initialize viewmodel
            setupWithSubBreedArgumentPassed()

            viewModel.viewState.test {
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify {
                    repository.getSubBreedImages(
                        breedName = sampleBreeds[1].name,
                        subBreedName = sampleBreeds[1].subBreeds[0]
                    )
                }

                assertEquals(
                    BreedDetailsViewState(
                        isLoading = false,
                        appBarTitle = "All ${sampleBreeds[1].name} ${sampleBreeds[1].subBreeds[0]} Images",
                        images = breedImages
                    ),
                    expectItem()
                )
                //verify no more interactions
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify { repository.getSubBreedImages(any(), any()) wasNot Called }
            }
        }

    @Test
    fun `assert that when breed name and subBreed name argument is passed, and call to network fails, the viewState is set correctly`() =
        runBlocking {
            //re-initialize viewmodel
            setupWithSubBreedArgumentPassed(shouldReturnError = true)

            viewModel.viewState.test {
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify {
                    repository.getSubBreedImages(
                        breedName = sampleBreeds[1].name,
                        subBreedName = sampleBreeds[1].subBreeds[0]
                    )
                }

                assertEquals(
                    BreedDetailsViewState(
                        isLoading = false,
                        appBarTitle = "All ${sampleBreeds[1].name} ${sampleBreeds[1].subBreeds[0]} Images",
                        error = "something went wrong"
                    ),
                    expectItem()
                )
                //verify no more interactions
                coVerify { repository.getBreedImages(any()) wasNot Called }
                coVerify { repository.getSubBreedImages(any(), any()) wasNot Called }
            }
        }
}