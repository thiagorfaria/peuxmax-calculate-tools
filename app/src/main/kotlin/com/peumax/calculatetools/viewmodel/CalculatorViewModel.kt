package com.peumax.calculatetools.viewmodel

import androidx.lifecycle.ViewModel
import com.peumax.calculatetools.domain.usecase.CalculateAngleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class CalculatorUiState {
    data class Filled(
        val topDiameter: String = "",
        val bottomDiameter: String = "",
        val height: String = "",
        val result: String = ""
    ) : CalculatorUiState()
}

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateAngleUseCase: CalculateAngleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CalculatorUiState>(CalculatorUiState.Filled())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onTopDiameterChange(value: String) {
        updateFilled { it.copy(topDiameter = value) }
        recalculate()
    }

    fun onBottomDiameterChange(value: String) {
        updateFilled { it.copy(bottomDiameter = value) }
        recalculate()
    }

    fun onHeightChange(value: String) {
        updateFilled { it.copy(height = value) }
        recalculate()
    }

    fun clearInputs() {
        _uiState.update { CalculatorUiState.Filled() }
    }

    private fun updateFilled(transform: (CalculatorUiState.Filled) -> CalculatorUiState.Filled) {
        _uiState.update { current ->
            if (current is CalculatorUiState.Filled) transform(current) else current
        }
    }

    private fun recalculate() {
        val current = _uiState.value as? CalculatorUiState.Filled ?: return
        val top = current.topDiameter.toDoubleOrNull()
        val bottom = current.bottomDiameter.toDoubleOrNull()
        val height = current.height.toDoubleOrNull()

        if (top != null && top > 0 && bottom != null && bottom > 0 && height != null && height > 0) {
            val angle = calculateAngleUseCase.execute(top, bottom, height)
            _uiState.update { (it as? CalculatorUiState.Filled)?.copy(result = "%.2f".format(angle)) ?: it }
        } else {
            _uiState.update { (it as? CalculatorUiState.Filled)?.copy(result = "") ?: it }
        }
    }
}
