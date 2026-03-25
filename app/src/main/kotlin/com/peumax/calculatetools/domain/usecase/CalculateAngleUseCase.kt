package com.peumax.calculatetools.domain.usecase

import kotlin.math.atan
import kotlin.math.roundToInt
import javax.inject.Inject

class CalculateAngleUseCase @Inject constructor() {

    fun execute(topDiameter: Double, bottomDiameter: Double, height: Double): Double {
        val radiusDiff = (bottomDiameter / 2.0) - (topDiameter / 2.0)
        val angleRadians = atan(radiusDiff / height)
        val angleDegrees = Math.toDegrees(angleRadians)
        return (angleDegrees * 100.0).roundToInt() / 100.0
    }
}
