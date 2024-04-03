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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
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
    val counter: Int

    data class LocalImage(
        @DrawableRes val imageModel: Int,
        override val counter: Int
    ) : ImageModel {
        companion object {
            private const val IMAGE_ID = "imageId"
            private const val COUNTER_ID = "counterId"
            val MAP_SAVER = mapSaver(
                save = { model ->
                    mapOf(
                        IMAGE_ID to model.imageModel,
                        COUNTER_ID to model.counter
                    )
                },
                restore = { map ->
                    LocalImage(
                        imageModel = map[IMAGE_ID]!! as Int,
                        counter = map[COUNTER_ID]!! as Int
                    )
                }
            )
            val LIST_SAVER = listSaver(
                save = {
                    listOf(
                        it.imageModel,
                        it.counter
                    )
                },
                restore = {
                    LocalImage(
                        imageModel = it[0] as Int,
                        counter = it[1] as Int
                    )
                }
            )
        }
    }

    data class NetworkImage(
        val url: String,
        override val counter: Int
    ) : ImageModel {
        companion object {
            private const val URL_ID = "urlId"
            private const val COUNTER_ID = "counterId"
            val MAP_SAVER = mapSaver(
                save = { model ->
                    mapOf(
                        URL_ID to model.url,
                        COUNTER_ID to model.counter
                    )
                },
                restore = { map ->
                    NetworkImage(
                        url = map[URL_ID]!! as String,
                        counter = map[COUNTER_ID]!! as Int
                    )
                }
            )
            val LIST_SAVER = listSaver(
                save = {
                    listOf(
                        it.url,
                        it.counter
                    )
                },
                restore = {
                    NetworkImage(
                        url = it[0] as String,
                        counter = it[1] as Int
                    )
                }
            )
        }
    }
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

        var counter1 by rememberSaveable(stateSaver = ImageModel.LocalImage.MAP_SAVER) {
            mutableStateOf(ImageModel.LocalImage(R.drawable.image1, 0))
        }

        var counter2 by rememberSaveable(stateSaver = ImageModel.LocalImage.MAP_SAVER) {
            mutableStateOf(ImageModel.LocalImage(R.drawable.image2, 0))
        }

        var counter3 by rememberSaveable(stateSaver = ImageModel.LocalImage.MAP_SAVER) {
            mutableStateOf(ImageModel.LocalImage(R.drawable.image3, 0))
        }
        var counter4 by rememberSaveable(stateSaver = ImageModel.NetworkImage.MAP_SAVER) {
            mutableStateOf(
                ImageModel.NetworkImage(
                    "https://www.kaggle.com/competitions/6799/images/header.png",
                    0
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageWithSlot(
                imageModel = counter1
            ) {
                ButtonWithIcon(
                    counter = counter1.counter,
                    onClick = { counter1 = counter1.copy(counter = counter1.counter + 1) }
                )
            }
            ImageWithSlot(
                imageModel = counter2
            ) {
                IconWithBadge(
                    counter = counter2.counter,
                    onClick = { counter2 = counter2.copy(counter = counter2.counter + 1) }
                )
            }
            ImageWithSlot(
                imageModel = counter3
            ) {
                ButtonWithIcon(
                    counter = counter3.counter,
                    onClick = { counter3 = counter3.copy(counter = counter3.counter + 1) }
                )
            }
            ImageWithSlot(
                imageModel = counter4
            ) {
                IconWithBadge(
                    counter = counter4.counter,
                    onClick = { counter4 = counter4.copy(counter = counter4.counter + 1) }
                )
            }
            AsyncImage(
                model = "https://www.kaggle.com/competitions/6799/images/header.png",
                contentDescription = "미도리야"
            )
        }
    }
}
