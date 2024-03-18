package dev.nunu.mobile.konkuk.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SimpleTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithPlaceholder() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") },
        placeholder = { Text("example@gmail.com") }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithIcons() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("placeholder") },
        leadingIcon = { Icon(Icons.Filled.Favorite, contentDescription = "Localized description") },
        trailingIcon = { Icon(Icons.Filled.Info, contentDescription = "Localized description") }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithErrorState() {
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    fun validate(text: String) {
        isError = text.count() < 5
    }

    Column {
        TextField(
            value = text,
            onValueChange = {
                text = it
                isError = false
            },
            singleLine = true,
            label = { Text(if (isError) "Email*" else "Email") },
            isError = isError,
            keyboardActions = KeyboardActions { validate(text) }
        )
        if (isError) {
            Text(
                text = "Email format is invalid.",
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

// implementation(libs.androidx.material.icons.extended)
@Preview(showBackground = true)
@Composable
fun PasswordTextFieldWithIconButton() {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    TextField(
        value = password,
        onValueChange = { password = it },
        singleLine = true,
        label = { Text("Enter password") },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithHideKeyboardOnImeAction() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldSample() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Preview(showBackground = true)
@Composable
fun StyledTextField() {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Enter text") },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

