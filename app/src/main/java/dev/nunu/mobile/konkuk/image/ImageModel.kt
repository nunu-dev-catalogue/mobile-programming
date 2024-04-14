package dev.nunu.mobile.konkuk.image

import androidx.annotation.DrawableRes
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver

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
