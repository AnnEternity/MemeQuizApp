package com.umain.basicandroidintegration.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.ui.theme.text
import com.umain.basicandroidintegration.ui.theme.title1

@Composable
fun MainQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: MainQuizViewModel = viewModel(),
    onNavigate: (QuizTheme) -> Unit
) {
    // Local UI state that tracks ViewModel's current state
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.emit(MainQuizViewEvent.ViewReady)
    }

    when (val currentUiState = uiState) {
        is MainQuizViewState.Error -> {
            Text(
                text = currentUiState.errorMessage,
                color = Color.Red,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 100.dp)
            )
        }

        is MainQuizViewState.Loading -> {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Main screen
        is MainQuizViewState.Loaded -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = "Quiz.",
                    style = title1,
                    autoSize = TextAutoSize.StepBased(),
                    modifier = Modifier,
                    overflow = TextOverflow.Clip,
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier,
                ) {
                    items(QuizTheme.entries) {
                        Card(
                            onClick = { onNavigate(it) },
                            modifier = Modifier
                                .padding(8.dp)
                                .width(160.dp)
                        ) {
                            Column() {
                                Image(
                                    modifier = Modifier
                                        .height(180.dp),
                                    painter = painterResource(it.image),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "${it.name} quiz image"
                                )
                                Text(
                                    text = it.name,
                                    style = text,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

val QuizTheme.image: Int
    get() = when (this) {
        QuizTheme.Cats -> R.drawable.cat_quiz
        QuizTheme.Dogs -> R.drawable.dog_quiz
        //QuizTheme.Mixed -> R.drawable.mix_quiz
    }
