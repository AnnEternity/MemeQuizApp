package com.umain.basicandroidintegration.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.presentation.MainViewState
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.title1
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
) {
    val uiState = remember { mutableStateOf(viewModel.state.value) }

    LaunchedEffect(key1 = true) {
        viewModel.emit(DetailViewEvent.ViewReady)
        launch {
            viewModel.state.collect {
                uiState.value = it
            }
        }
    }

    when (uiState.value) {
        is DetailViewState.Error -> {
            val value = uiState.value as MainViewState.Error
            Text(
                text = value.errorMessage,
                color = Color.Red,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 100.dp)
            )
        }

        is DetailViewState.Loaded -> {
            val question = (uiState.value as DetailViewState.Loaded).question
            val (questionText, questionImage) = when (question) {
                CatQuizQuestions.BBB -> "Is this the meme lord known as ЪYЪ?" to R.drawable.byb_cat
                CatQuizQuestions.CHIPICHAPA -> "Is this our beloved CHIPI CHAPA?" to R.drawable.chipi_chapa_cat
                CatQuizQuestions.HUH -> "Is this the legendary HUH cat?" to R.drawable.huh_cat
                CatQuizQuestions.MAX -> "Is this Max - the cat who's seen things?" to R.drawable.maxwell_cat
                CatQuizQuestions.HAPPY -> "Does this cat give off happy vibes?" to R.drawable.happy_cat
                CatQuizQuestions.POP -> "Be honest - is that POP cat?" to R.drawable.pop_cat
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Big cat test",
                    style = title1,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = questionText,
                    style = subtitle,
                    textAlign = TextAlign.Center,
                )
                Image(
                    painter = painterResource(questionImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(350.dp),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.emit(DetailViewEvent.YesAnswer) },
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.White,
                            disabledContentColor = Color.Black
                        ),
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Image(
                                painter = painterResource(R.drawable.paw_icon),
                                contentDescription = "Cat's paw"
                            )
                            Text(text = "Yes", style = subtitle)
                        }

                    }
                    OutlinedButton(
                        onClick = {
                            viewModel.emit(DetailViewEvent.NoAnswer)
                        },
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.White,
                            disabledContentColor = Color.Black
                        ),
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Image(
                                painter = painterResource(R.drawable.paw_icon_dark),
                                contentDescription = "Cat's paw"
                            )
                            Text(text = "No", style = subtitle)
                        }

                    }
                }


            }
        }

        is DetailViewState.Loading -> {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DetailViewState.QuizEnd -> {
            val result = (uiState.value as DetailViewState.QuizEnd).result
            val (text, image) = when (result) {
                CatQuizResults.NotACat -> "Lost..." to R.drawable.not_a_cat
                CatQuizResults.LittleBit -> "Confused kitten" to R.drawable.little_bit
                CatQuizResults.MediumCat -> "Casual Cat scroller" to R.drawable.medium_cat
                CatQuizResults.BetterThanHalf -> "Meme pawprentice" to R.drawable.better_than_half
                CatQuizResults.BigCat -> "Certified Cat Meme Connoisseur!" to R.drawable.big_cat
                CatQuizResults.Best -> "The chosen Meow!" to R.drawable.best_cat
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = text, style = title1, textAlign = TextAlign.Center)
                Image(
                    painter = painterResource(image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(350.dp)
                )
                OutlinedButton(
                    onClick = {
                        viewModel.emit(DetailViewEvent.ViewReady)
                    },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    ),
                    border = BorderStroke(0.5.dp, Color.LightGray)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Image(
                            painter = painterResource(R.drawable.paw_icon),
                            contentDescription = "Cat's paw"
                        )
                        Text(text = "One more time?", style = subtitle)
                    }

                }
            }
        }

    }
}