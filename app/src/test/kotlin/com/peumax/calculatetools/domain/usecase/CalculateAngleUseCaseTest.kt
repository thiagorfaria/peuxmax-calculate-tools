package com.peumax.calculatetools.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateAngleUseCaseTest {

    private lateinit var useCase: CalculateAngleUseCase

    @Before
    fun setUp() {
        useCase = CalculateAngleUseCase()
    }

    @Test
    fun `given top=10 bottom=60 height=30, result should be 39_81 degrees`() {
        val result = useCase.execute(
            topDiameter = 10.0,
            bottomDiameter = 60.0,
            height = 30.0
        )
        assertEquals(39.81, result, 0.01)
    }

    @Test
    fun `given equal top and bottom diameters, angle should be zero`() {
        val result = useCase.execute(
            topDiameter = 30.0,
            bottomDiameter = 30.0,
            height = 20.0
        )
        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun `given small top and large bottom, angle should be positive`() {
        val result = useCase.execute(
            topDiameter = 5.0,
            bottomDiameter = 50.0,
            height = 40.0
        )
        assert(result > 0.0)
    }
}
