package com.example.dogbreed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.features.all_breeds.AllBreedViewState
import com.example.dogbreed.features.all_breeds.AllBreedsViewModel
import com.example.dogbreed.util.Resource
import com.example.dogbreed.util.SampleData.sampleBreeds
import com.example.dogbreed.util.TestCoroutineRule
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AllBreedsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testDispatcher = TestCoroutineRule()

    private val repository = mockk<DogBreedRepository>()
    private lateinit var viewModel: AllBreedsViewModel

    @Before
    fun defaultSetup() {
        coEvery { repository.getBreedStream() } returns flowOf(Resource.Success(sampleBreeds[0]))
        viewModel = AllBreedsViewModel(repository)
    }

    private fun setupWithError() {
        coEvery { repository.getBreedStream() } returns flowOf(Resource.Error(message = "error occurred"))
        viewModel = AllBreedsViewModel(repository)
    }

    @Test
    fun `assert when viewModel is initialized and response is successful, viewState is correctly set`() =
        runBlocking {
            coVerify { repository.getBreedStream() }

            viewModel.viewState.test {
                assertEquals(AllBreedViewState(listOf(sampleBreeds[0]), false), expectItem())
                //verify no more interactions
                coVerify { repository.getBreedStream() wasNot Called }
            }
        }

    @Test
    fun `assert when viewModel is initialized and response returns error, viewState is correctly set`() =
        runBlocking {
            setupWithError()

            coVerify { repository.getBreedStream() }
            viewModel.viewState.test {
                assertEquals(
                    AllBreedViewState(error = "error occurred", isLoading = false),
                    expectItem()
                )
                //verify no more interactions
                coVerify { repository.getBreedStream() wasNot Called }
            }
        }
}