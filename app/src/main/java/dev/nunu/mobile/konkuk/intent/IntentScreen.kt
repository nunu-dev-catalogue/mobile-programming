package dev.nunu.mobile.konkuk.intent

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme

@Composable
fun IntentScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        IntentButton(onClick = {
            val webpage = Uri.parse("http://www.naver.com")
            val webIntent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(context, webIntent, null)
        }) {
            Text(text = "네이버")
        }
        IntentButton(onClick = {
            val location = Uri.parse("geo:37.543684,127.077130?z=16")
            val mapIntent = Intent(Intent.ACTION_VIEW, location)
            startActivity(context, mapIntent, null)
        }) {
            Text(text = "맵")
        }
        IntentButton(onClick = {
            val message = Uri.parse("sms:010-1234-1234")
            val messageIntent = Intent(Intent.ACTION_SENDTO, message)
            messageIntent.putExtra("sms_body", "밥 먹자....")
            startActivity(context, messageIntent, null)
        }) {
            Text(text = "문자보내기")
        }
        IntentButton(onClick = {
            val webpage = Uri.parse("https://www.youtube.com/watch?v=hDRxFmPph6Y")
            val webIntent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(context, webIntent, null)
        }) {
            Text(text = "Youtube")
        }
        IntentButton(onClick = {
            val number = Uri.parse("tel:010-1234-1234")
            val callIntent = Intent(Intent.ACTION_CALL, number)
            startActivity(context, callIntent, null)
        }) {
            Text(text = "전화걸기")
        }
    }
}

@Composable
private fun IntentButton(
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), content = content)
}

@Preview
@Composable
private fun PreviewIntentScreen() {
    MobileProgrammingTheme {
        IntentScreen()
    }
}
