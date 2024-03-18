package dev.nunu.mobile.konkuk

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme
import java.text.NumberFormat
import kotlin.math.ceil

@Composable
private fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
private fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

data class CalculatorState(
    val billAmount: String = "",
    val tipPercentage: String = "",
    val isRoundUp: Boolean = false
) {
    val tipAmount: String
        get() {
            if (billAmount.isEmpty() || tipPercentage.isEmpty()) {
                return ""
            }
            val tip = if (isRoundUp) {
                (tipPercentage.toDouble()) / 100 * (billAmount.toDouble())
            } else {
                ceil((tipPercentage.toDouble()) / 100 * (billAmount.toDouble()))
            }
            return NumberFormat.getCurrencyInstance().format(tip)
        }

    companion object {
        val Saver: Saver<CalculatorState, *> = listSaver(
            save = { listOf(it.billAmount, it.tipPercentage, it.isRoundUp) },
            restore = {
                CalculatorState(
                    billAmount = it[0] as String,
                    tipPercentage = it[1] as String,
                    isRoundUp = it[2] as Boolean
                )
            }
        )
    }
}


@Composable
fun rememberCalculateState(
    billAmount: String = "",
    tipPercentage: String = "",
    isRoundUp: Boolean = false
) = rememberSaveable(stateSaver = CalculatorState.Saver) {
    mutableStateOf(CalculatorState(billAmount, tipPercentage, isRoundUp))
}

@Composable
fun CalculatorScreen() {
    var state by rememberCalculateState()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Calculate Tip")
        VerticalSpacer(height = 16.dp)
        OutlinedTextField(
            value = state.billAmount,
            onValueChange = { state = state.copy(billAmount = it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Bill Amount") },
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Money,
                    contentDescription = "Money"
                )
            },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        VerticalSpacer(height = 32.dp)
        OutlinedTextField(
            value = state.tipPercentage,
            onValueChange = { state = state.copy(tipPercentage = it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Tip Percentage") },
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Money,
                    contentDescription = "Money"
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        VerticalSpacer(height = 32.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round up tip?")
            Switch(
                checked = state.isRoundUp,
                onCheckedChange = { state = state.copy(isRoundUp = it) })
        }
        VerticalSpacer(height = 32.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tip Amount: ${state.tipAmount}", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    MobileProgrammingTheme {
        CalculatorScreen()
    }
}
