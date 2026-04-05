package com.peumax.calculatetools.viewmodel

import com.peumax.calculatetools.domain.usecase.CalculateAngleUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorViewModelTest {

    private lateinit var useCase: CalculateAngleUseCase
    private lateinit var viewModel: CalculatorViewModel

    @BeforeEach
    fun setUp() {
        useCase = mockk()
        every { useCase.execute(any(), any(), any()) } returns 45.0
        viewModel = CalculatorViewModel(useCase)
    }

    private val state get() = viewModel.uiState.value as CalculatorUiState.Filled

    @Test
    fun `initial state should have all fields empty`() {
        assertEquals("", state.topDiameter)
        assertEquals("", state.bottomDiameter)
        assertEquals("", state.height)
        assertEquals("", state.result)
    }

    @Test
    fun `onTopDiameterChange should update topDiameter in state`() {
        viewModel.onTopDiameterChange("100")
        assertEquals("100", state.topDiameter)
    }

    @Test
    fun `onBottomDiameterChange should update bottomDiameter in state`() {
        viewModel.onBottomDiameterChange("200")
        assertEquals("200", state.bottomDiameter)
    }

    @Test
    fun `onHeightChange should update height in state`() {
        viewModel.onHeightChange("50")
        assertEquals("50", state.height)
    }

    @Test
    fun `when all inputs are valid, result should be calculated and formatted`() {
        every { useCase.execute(100.0, 200.0, 50.0) } returns 45.0

        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("50")

        assertEquals("45.00", state.result)
    }

    @Test
    fun `when height is zero, result should be empty`() {
        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("0")

        assertEquals("", state.result)
    }

    @Test
    fun `when any input is missing, result should be empty`() {
        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("200")

        assertEquals("", state.result)
    }

    @Test
    fun `when height is negative, result should be empty`() {
        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("-5")

        assertEquals("", state.result)
    }

    @Test
    fun `when topDiameter is negative, result should be empty`() {
        viewModel.onTopDiameterChange("-10")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("50")

        assertEquals("", state.result)
    }

    @Test
    fun `when bottomDiameter is negative, result should be empty`() {
        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("-20")
        viewModel.onHeightChange("50")

        assertEquals("", state.result)
    }

    @Test
    fun `when input is non-numeric, result should be empty`() {
        viewModel.onTopDiameterChange("abc")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("50")

        assertEquals("", state.result)
    }

    @Test
    fun `clearInputs should reset all fields to empty`() {
        viewModel.onTopDiameterChange("100")
        viewModel.onBottomDiameterChange("200")
        viewModel.onHeightChange("50")

        viewModel.clearInputs()

        assertEquals("", state.topDiameter)
        assertEquals("", state.bottomDiameter)
        assertEquals("", state.height)
        assertEquals("", state.result)
    }
}
