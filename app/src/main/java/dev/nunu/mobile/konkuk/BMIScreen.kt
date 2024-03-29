package dev.nunu.mobile.konkuk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme

@Composable
private fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
private fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

data class BmiState(
    val height: String = "",
    val weight: String = "",
    val isMeter: Boolean = false
) {
    val bmi: String
        get() {
            if (height.isEmpty() || weight.isEmpty()) {
                return "BMI 체크"
            }
            val bmi = if (isMeter) {
                (weight.toDouble()) / (height.toDouble() * height.toDouble())
            } else {
                val heightInMeter = height.toDouble() / 100
                (weight.toDouble()) / (heightInMeter * heightInMeter)
            }
            return when (bmi) {
                in 0.0..<18.5 -> "저체중"
                in 18.5..<25.0 -> "정상"
                in 25.0..<30.0 -> "과체중"
                else -> "비만"
            }
        }
    val meterPlaceholder
        get() = if (isMeter) "m" else "cm"

    companion object {
        val Saver: Saver<BmiState, *> = listSaver(
            save = { listOf(it.height, it.weight, it.isMeter) },
            restore = {
                BmiState(
                    height = it[0] as String,
                    weight = it[1] as String,
                    isMeter = it[2] as Boolean
                )
            }
        )
    }
}


@Composable
fun rememberBmiState() = rememberSaveable(stateSaver = BmiState.Saver) {
    mutableStateOf(BmiState())
}

val LocalDispatcher = staticCompositionLocalOf<(BmiState) -> Unit> {
    error("No Dispatcher provided")
}

@Composable
fun BMIScreen() {
    var state by rememberBmiState()
    val focusManager = LocalFocusManager.current
    CompositionLocalProvider(
        LocalDispatcher provides { state = it }
    ) {
        val dispatcher = LocalDispatcher.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "201811218 이현우")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "키 입력 단위 미터(m)?")
                HorizontalSpacer(width = 12.dp)
                Switch(
                    checked = state.isMeter,
                    onCheckedChange = { dispatcher(state.copy(isMeter = it)) }
                )
            }
            VerticalSpacer(height = 16.dp)
            OutlinedTextField(
                value = state.height,
                onValueChange = { dispatcher(state.copy(height = it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("키(${state.meterPlaceholder})") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            VerticalSpacer(height = 32.dp)
            OutlinedTextField(
                value = state.weight,
                onValueChange = { state = state.copy(weight = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("몸무게(kg)") },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            VerticalSpacer(height = 32.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(state.bmi, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    MobileProgrammingTheme {
        BMIScreen()
    }
}
