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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
                ceil((tipPercentage.toDouble()) / 100 * (billAmount.toDouble()))
            } else {
                (tipPercentage.toDouble()) / 100 * (billAmount.toDouble())
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
        Text("201811218 이현우")
        Text(text = stringResource(id = R.string.calculator_tip))
        VerticalSpacer(height = 16.dp)
        NumberTextField(
            text = state.billAmount,
            onValueChange = { state = state.copy(billAmount = it) },
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.bill_amount),
            icon = Icons.Filled.Money,
            action = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        VerticalSpacer(height = 32.dp)
        NumberTextField(
            icon = Icons.Filled.Money,
            text = state.tipPercentage,
            onValueChange = { state = state.copy(tipPercentage = it) },
            label = stringResource(id = R.string.how_was_the_service),
            modifier = Modifier.fillMaxWidth(),
            action = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        VerticalSpacer(height = 32.dp)
        SwitchRow(
            label = stringResource(id = R.string.round_up_tip),
            value = state.isRoundUp,
            onValueChange = { state = state.copy(isRoundUp = it) }
        )
        VerticalSpacer(height = 32.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.tip_amount, state.tipAmount),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun NumberTextField(
    icon: ImageVector,
    text: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    action: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        leadingIcon = {
            Image(
                imageVector = icon,
                contentDescription = "Money"
            )
        },
        keyboardActions = action
    )
}

@Composable
fun SwitchRow(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Switch(
            checked = value,
            onCheckedChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    MobileProgrammingTheme {
        CalculatorScreen()
    }
}
