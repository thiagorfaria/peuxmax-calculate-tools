package com.peumax.calculatetools.viewmodel

import androidx.lifecycle.ViewModel
import com.peumax.calculatetools.domain.usecase.CalculateAngleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CalculatorUiState(
    val topDiameter: String = "",
    val bottomDiameter: String = "",
    val height: String = "",
    val result: String = ""
)

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateAngleUseCase: CalculateAngleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onTopDiameterChange(value: String) {
        _uiState.update { it.copy(topDiameter = value) }
        recalculate()
    }

    fun onBottomDiameterChange(value: String) {
        _uiState.update { it.copy(bottomDiameter = value) }
        recalculate()
    }

    fun onHeightChange(value: String) {
        _uiState.update { it.copy(height = value) }
        recalculate()
    }

    fun clearInputs() {
        _uiState.update { CalculatorUiState() }
    }

    private fun recalculate() {
        val state = _uiState.value
        val top = state.topDiameter.toDoubleOrNull()
        val bottom = state.bottomDiameter.toDoubleOrNull()
        val height = state.height.toDoubleOrNull()

        if (top != null && bottom != null && height != null && height > 0) {
            val angle = calculateAngleUseCase.execute(top, bottom, height)
            _uiState.update { it.copy(result = "%.2f".format(angle)) }
        } else {
            _uiState.update { it.copy(result = "") }
        }
    }
}
