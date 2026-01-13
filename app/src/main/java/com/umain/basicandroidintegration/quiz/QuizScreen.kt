package com.umain.basicandroidintegration.quiz

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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.presentation.MainViewState
import com.umain.basicandroidintegration.quiz.cat.image
import com.umain.basicandroidintegration.quiz.cat.questionImage
import com.umain.basicandroidintegration.quiz.cat.questionText
import com.umain.basicandroidintegration.quiz.cat.text
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.title1
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(),
    navigateToStart: () -> Unit
) {
    val uiState = remember { mutableStateOf(viewModel.state.value) }

    LaunchedEffect(key1 = true) {
        viewModel.emit(QuizViewEvent.ViewReady)
        launch {
            viewModel.state.collect {
                uiState.value = it
            }
        }
    }

    when (uiState.value) {
        is QuizViewState.Error -> {
            val value = uiState.value as MainViewState.Error
            Text(
                text = value.errorMessage,
                color = Color.Red,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 100.dp)
            )
        }

        is QuizViewState.Loaded -> {
            val question = (uiState.value as QuizViewState.Loaded).question
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = "Big cat test",
                    style = title1,
                    //textAlign = TextAlign.Center,
                    autoSize = TextAutoSize.StepBased(),
                    maxLines = 1,
                )
                BasicText(
                    text = question.questionText,
                    style = subtitle,
                    //textAlign = TextAlign.Center,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 52.sp),
                    maxLines = 1,
                )
                Image(
                    painter = painterResource(question.questionImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(350.dp),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.emit(QuizViewEvent.YesAnswer) },
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
                            viewModel.emit(QuizViewEvent.NoAnswer)
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

        is QuizViewState.Loading -> {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is QuizViewState.QuizEnd -> {
            val result = (uiState.value as QuizViewState.QuizEnd).result
            val score = (uiState.value as QuizViewState.QuizEnd).score
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = result.text,
                    style = title1,
                    autoSize = TextAutoSize.StepBased(),
                    maxLines = 1,
                )
                BasicText(
                    text = stringResource(R.string.score, score),
                    style = subtitle,
                    //textAlign = TextAlign.Center,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 48.sp),
                    maxLines = 1,
                )
                Image(
                    painter = painterResource(result.image),
                    contentDescription = result.text,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(350.dp),
                )
                OutlinedButton(
                    onClick = navigateToStart,
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