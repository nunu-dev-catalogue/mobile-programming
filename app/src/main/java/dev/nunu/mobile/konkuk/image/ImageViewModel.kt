package dev.nunu.mobile.konkuk.image

import androidx.lifecycle.ViewModel
import dev.nunu.mobile.konkuk.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ImageViewModel : ViewModel() {
    private val _imageList = MutableStateFlow<List<ImageModel>>(emptyList())
    val imageList = _imageList.asStateFlow()

    init {
        _imageList.value = listOf(
            ImageModel.LocalImage(imageModel = R.drawable.image1, counter = 0),
            ImageModel.LocalImage(imageModel = R.drawable.image2, counter = 0),
            ImageModel.LocalImage(imageModel = R.drawable.image3, counter = 0),
            ImageModel.NetworkImage(
                url = "https://www.kaggle.com/competitions/6799/images/header.png",
                counter = 0
            )
        )
    }

    fun increaseCounterOf(index: Int) {
        _imageList.update { list ->
            list.mapIndexed { i, imageModel ->
                if (i == index) {
                    when (imageModel) {
                        is ImageModel.LocalImage -> imageModel.copy(counter = imageModel.counter + 1)
                        is ImageModel.NetworkImage -> imageModel.copy(counter = imageModel.counter + 1)
                    }
                } else {
                    imageModel
                }
            }
        }
    }
}
