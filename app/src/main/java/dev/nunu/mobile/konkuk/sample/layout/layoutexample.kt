package dev.nunu.mobile.konkuk.sample.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ArtistCard() {
    Text("Alfred Sisley")
    Text("3 minutes ago")
}

@Composable
fun ArtistCardColumn() {
    Column {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

data class Artist(val image: Painter, val name: String, val lastSeenOnline: String)

@Composable
fun ArtistCardRow(artist: Artist) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = artist.image, contentDescription = "Artist image")
        Column {
            Text(artist.name)
            Text(artist.lastSeenOnline)
        }
    }
}


@Composable
fun ArtistAvatar(artist: Artist) {
    Box {
        Image(
            painter = artist.image,
            contentDescription = "Artist image"
        )
        Icon(
            Icons.Filled.Check,
            contentDescription = "Check mark"
        )
    }
}
