package dev.nunu.mobile.konkuk

import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.Assertions.*

class BmiStateTest : BehaviorSpec({
    given("when user input weight and height") {
        `when`("meter is enabled") {
            then("meterPlaceHolder is m") {
                val bmiState = BmiState("1.0", "70.0", isMeter = true)
                assertEquals("m", bmiState.meterPlaceholder)
            }
            then("bmi is calculated") {
                val bmiState = BmiState("1.0", "70.0", isMeter = true)
                assertEquals("비만", bmiState.bmi)
            }
        }
        `when`("meter is disabled") {
            then("meterPlaceHolder is cm") {
                val bmiState = BmiState("100.0", "70.0", isMeter = false)
                assertEquals("cm", bmiState.meterPlaceholder)
            }
            then("bmi is calculated") {
                val bmiState = BmiState("100.0", "70.0", isMeter = false)
                assertEquals("비만", bmiState.bmi)
            }
        }
    }
    given("when user didn't input weight or height") {
        `when`("user didn't input weight") {
            then("bmi is not calculated") {
                val bmiState = BmiState("", "1.0", isMeter = true)
                assertEquals("BMI 체크", bmiState.bmi)
            }
        }
        `when`("user didn't input height") {
            then("bmi is not calculated") {
                val bmiState = BmiState("70.0", "", isMeter = true)
                assertEquals("BMI 체크", bmiState.bmi)
            }
        }
    }
})
