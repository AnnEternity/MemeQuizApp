package com.umain.basicandroidintegration.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.storage.Score
import com.umain.basicandroidintegration.ui.theme.Blue
import com.umain.basicandroidintegration.ui.theme.Dimens
import com.umain.basicandroidintegration.ui.theme.Orange
import com.umain.basicandroidintegration.ui.theme.Pink
import com.umain.basicandroidintegration.ui.theme.Yellow
import com.umain.basicandroidintegration.ui.theme.smallTitle
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.text
import com.umain.basicandroidintegration.ui.theme.title

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
                color = Orange,
                style = subtitle,
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.medium, vertical = Dimens.imageSmall)
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
                modifier =
                    Modifier
                        .padding(
                            end = Dimens.large,
                            start = Dimens.large,
                            bottom = Dimens.large
                        )
                        .systemBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(Dimens.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = stringResource(R.string.quiz_title),
                    style = title,
                    autoSize = TextAutoSize.StepBased(),
                    modifier = Modifier,
                    overflow = TextOverflow.Clip
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuizTheme.entries.forEach { QuizCard(onNavigate, it) }
                }
                ScoreCard(currentUiState.score)
            }
        }
    }
}

@Composable
private fun ScoreCard(score: List<Score>) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Orange,
                        Blue,
                        Pink,
                        Yellow
                    )
                ),
                shape = RoundedCornerShape(Dimens.XLarge)
            )
            .padding(2.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.large),
                verticalArrangement = Arrangement.spacedBy(Dimens.small)
            ) {
                Text(
                    text = stringResource(R.string.score_title),
                    style = smallTitle,
                    modifier = Modifier
                )
                Divider(
                    color = Pink,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (score.isEmpty()) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.be_first_to_score),
                        style = subtitle
                    )
                }
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(bottom = Dimens.large)
                ) {
                    items(score) { ScoreItem(it) }
                }
            }
        }
    }
}

@Composable
private fun ScoreItem(score1: Score) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier =
                Modifier
                    .width(Dimens.textLarge)
                    .basicMarquee(),
            text = score1.name,
            maxLines = 1
        )
        Text(text = score1.quizTheme.toString())
        Text(text = stringResource(R.string.score_rate, score1.score, score1.maxPossible))
    }
}

@Composable
private fun QuizCard(onNavigate: (QuizTheme) -> Unit, theme: QuizTheme) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Orange,
                        Blue,
                        Pink,
                        Yellow
                    )
                ),
                shape = RoundedCornerShape(Dimens.medium)
            )
            .padding(2.dp)
    ) {
        Card(
            onClick = { onNavigate(theme) },
            modifier = Modifier.width(Dimens.imageWidth)
        ) {
            Column {
                Image(
                    modifier = Modifier.height(Dimens.imageHeight),
                    painter = painterResource(theme.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.quiz_image, theme.name)
                )
                Text(
                    text = theme.name,
                    style = text,
                    modifier = Modifier.padding(Dimens.small)
                )
            }
        }
    }
}

val QuizTheme.image: Int
    get() =
        when (this) {
            QuizTheme.Cats -> R.drawable.cat_quiz
            QuizTheme.Dogs -> R.drawable.dog_quiz
        }
