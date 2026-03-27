package com.peumax.calculatetools.viewmodel

import com.peumax.calculatetools.domain.usecase.CalculateAngleUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        viewModel = CalculatorViewModel(CalculateAngleUseCase())
    }

    @Test
    fun `initial state should have all fields empty and no result`() {
        val state = viewModel.uiState.value
        assertEquals("", state.topDiameter)
        assertEquals("", state.bottomDiameter)
        assertEquals("", state.height)
        assertEquals("", state.result)
    }

    @Test
    fun `onTopDiameterChange should update topDiameter in state`() {
        viewModel.onTopDiameterChange("10")
        assertEquals("10", viewModel.uiState.value.topDiameter)
    }

    @Test
    fun `onBottomDiameterChange should update bottomDiameter in state`() {
        viewModel.onBottomDiameterChange("60")
        assertEquals("60", viewModel.uiState.value.bottomDiameter)
    }

    @Test
    fun `onHeightChange should update height in state`() {
        viewModel.onHeightChange("30")
        assertEquals("30", viewModel.uiState.value.height)
    }

    @Test
    fun `when all inputs are valid result should be calculated`() {
        viewModel.onTopDiameterChange("10")
        viewModel.onBottomDiameterChange("60")
        viewModel.onHeightChange("30")
        assertEquals("39.81", viewModel.uiState.value.result)
    }

    @Test
    fun `when any input is missing result should be empty`() {
        viewModel.onTopDiameterChange("10")
        viewModel.onBottomDiameterChange("60")
        assertEquals("", viewModel.uiState.value.result)
    }

    @Test
    fun `clearInputs should reset all fields and result to empty`() {
        viewModel.onTopDiameterChange("10")
        viewModel.onBottomDiameterChange("60")
        viewModel.onHeightChange("30")

        viewModel.clearInputs()

        val state = viewModel.uiState.value
        assertEquals("", state.topDiameter)
        assertEquals("", state.bottomDiameter)
        assertEquals("", state.height)
        assertEquals("", state.result)
    }
}
