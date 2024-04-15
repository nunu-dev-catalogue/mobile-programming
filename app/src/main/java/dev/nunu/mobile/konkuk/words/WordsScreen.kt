package dev.nunu.mobile.konkuk.words

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nunu.mobile.konkuk.ui.theme.MobileProgrammingTheme
import kotlinx.coroutines.launch

@Stable
data class Word(
    val word: String,
    val meaning: String
)


@Composable
fun rememberWordsState(
    fileName: String,
    context: Context = LocalContext.current
) = rememberSaveable(fileName) {
    val words = context.assets.open(fileName)
        .bufferedReader()
        .use { it.readLines() }
    words.chunked(2) { (word, meaning) -> Word(word, meaning) }
}


@Composable
fun WordItem(word: Word) {
    Text(
        text = word.word,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScroll) ->
            if (currentIndex != lastIndex || currentScroll != lastScroll) {
                value = currentIndex < lastIndex ||
                        (currentIndex == lastIndex && currentScroll < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScroll
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen() {
    var words = rememberWordsState("words.txt")
    var wordsState by remember(words) {
        mutableStateOf(words)
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            // 스크롤을 위로 하면 버튼이 보임
            AnimatedVisibility(
                visible = listState.isScrollingUp().value,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                FloatingActionButton(
                    onClick = { scope.launch { listState.animateScrollToItem(0) } }
                ) {
                    Text(text = "Top")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "201811218 이현우",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
            items(wordsState) { word ->
                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            wordsState = wordsState.toMutableList().also { wordList -> wordList.remove(word) }
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(
                    state = swipeToDismissBoxState,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red, shape = RoundedCornerShape(12.dp))
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WordItem(word = word)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordsScreenPreview() {
    MobileProgrammingTheme {
        WordsScreen()
    }
}

