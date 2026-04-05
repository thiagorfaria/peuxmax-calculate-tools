package com.peumax.calculatetools.domain.usecase

import kotlin.math.atan
import javax.inject.Inject

class CalculateAngleUseCase @Inject constructor() {

    fun execute(topDiameter: Double, bottomDiameter: Double, height: Double): Double {
        val radiusDiff = (bottomDiameter / 2.0) - (topDiameter / 2.0)
        val angleRadians = atan(radiusDiff / height)
        return Math.toDegrees(angleRadians)
    }
}
