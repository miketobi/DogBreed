package com.example.dogbreed

import com.example.dogbreed.util.GenerateTitleName
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenerateTitleNameTest {

    lateinit var generateTitleName: GenerateTitleName

    @Before
    fun setup() {
        generateTitleName = GenerateTitleName()
    }

    @Test
    fun `assert when breedName and subBreedName is not empty`() {
        val expectedValue = "All Dog SubDog Images"
        val result = generateTitleName.getTitle("Dog", "SubDog")
        assertEquals(expectedValue, result)
    }

    @Test
    fun `assert when subBreedName is empty`() {
        val expectedValue = "All Dog Images"
        val result = generateTitleName.getTitle("Dog")
        assertEquals(expectedValue, result)
    }

    @Test
    fun `assert when subBreedName is null`() {
        val expectedValue = "All Dog Images"
        val result = generateTitleName.getTitle("Dog", null)
        assertEquals(expectedValue, result)
    }
}