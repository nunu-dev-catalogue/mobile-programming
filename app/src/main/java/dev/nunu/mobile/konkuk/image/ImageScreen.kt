package dev.nunu.mobile.konkuk.image

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.nunu.mobile.konkuk.R


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
    val imageViewModel = viewModel<ImageViewModel>()
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

        val state by imageViewModel.imageList.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageWithSlot(
                imageModel = state[0]
            ) {
                ButtonWithIcon(
                    counter = state[0].counter,
                    onClick = { imageViewModel.increaseCounterOf(0) }
                )
            }
            ImageWithSlot(
                imageModel = state[1]
            ) {
                IconWithBadge(
                    counter = state[1].counter,
                    onClick = { imageViewModel.increaseCounterOf(1) }
                )
            }
            ImageWithSlot(
                imageModel = state[2]
            ) {
                ButtonWithIcon(
                    counter = state[2].counter,
                    onClick = { imageViewModel.increaseCounterOf(2) }
                )
            }
            ImageWithSlot(
                imageModel = state[3]
            ) {
                IconWithBadge(
                    counter = state[3].counter,
                    onClick = { imageViewModel.increaseCounterOf(3) }
                )
            }
            AsyncImage(
                model = (state[4] as ImageModel.NetworkImage).url,
                contentDescription = "미도리야"
            )
        }
    }
}
