package dev.nunu.mobile.konkuk.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.nunu.mobile.konkuk.R

sealed interface ImageModel {
    data class LocalImage(@DrawableRes val imageModel: Int) : ImageModel
    data class NetworkImage(val url: String) : ImageModel
}

@Composable
fun ImageWithSlot(
    imageModel: ImageModel,
    bottomSlot: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (imageModel) {
            is ImageModel.LocalImage -> {
                Image(
                    painter = painterResource(id = imageModel.imageModel),
                    contentDescription = "null"
                )
            }

            is ImageModel.NetworkImage -> {
                AsyncImage(model = imageModel.url, contentDescription = "Null")
            }
        }
        bottomSlot()
    }
}

@Composable
fun ButtonWithIcon(
    counter: Int,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.Red
        )
        if (counter > 0) {
            Text("$counter")
        } else {
            Text("Like")
        }
    }
}

@Composable
fun IconWithBadge(
    counter: Int,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        BadgedBox(badge = { Badge { Text("$counter") } }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}


@Composable
fun ImageScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scrollState = rememberScrollState()

        var counter1 by rememberSaveable {
            mutableIntStateOf(0)
        }

        var counter2 by rememberSaveable {
            mutableIntStateOf(5)
        }
        var counter3 by rememberSaveable {
            mutableIntStateOf(20)
        }
        var counter4 by rememberSaveable {
            mutableIntStateOf(50)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageWithSlot(
                imageModel = ImageModel.LocalImage(R.drawable.image1)
            ) {
                ButtonWithIcon(
                    counter = counter1,
                    onClick = { counter1++ }
                )
            }
            ImageWithSlot(
                imageModel = ImageModel.LocalImage(R.drawable.image2)
            ) {
                IconWithBadge(
                    counter = counter2,
                    onClick = { counter2++ }
                )
            }
            ImageWithSlot(
                imageModel = ImageModel.LocalImage(R.drawable.image3)
            ) {
                ButtonWithIcon(
                    counter = counter3,
                    onClick = { counter3++ }
                )
            }
            AsyncImage(
                model = "https://www.kaggle.com/competitions/6799/images/header.png",
                contentDescription = "미도리야"
            )
        }
    }
}
