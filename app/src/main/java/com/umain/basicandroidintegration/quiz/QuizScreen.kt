package com.umain.basicandroidintegration.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.quiz.data.image
import com.umain.basicandroidintegration.quiz.data.questionImage
import com.umain.basicandroidintegration.quiz.data.questionText
import com.umain.basicandroidintegration.quiz.data.resultText
import com.umain.basicandroidintegration.ui.theme.BasicAndroidIntegrationTheme
import com.umain.basicandroidintegration.ui.theme.Blue
import com.umain.basicandroidintegration.ui.theme.Dimens
import com.umain.basicandroidintegration.ui.theme.LightGray
import com.umain.basicandroidintegration.ui.theme.Orange
import com.umain.basicandroidintegration.ui.theme.Pink
import com.umain.basicandroidintegration.ui.theme.White
import com.umain.basicandroidintegration.ui.theme.Yellow
import com.umain.basicandroidintegration.ui.theme.components.AnswerButton
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.title

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(),
    navigateToStart: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) { viewModel.emit(QuizViewEvent.ViewReady) }

    when (val currentUiState = uiState) {
        is QuizViewState.Error -> {
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

        is QuizViewState.Loaded -> {
            val question = currentUiState.question
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier =
                        Modifier
                            .padding(horizontal = Dimens.XXLarge)
                            .statusBarsPadding()
                            .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        Dimens.small,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicText(
                        text = stringResource(R.string.quiz_theme_title, currentUiState.themeText),
                        style = title,
                        autoSize = TextAutoSize.StepBased(),
                        maxLines = 1
                    )
                    BasicText(
                        text = question.questionText,
                        style = subtitle,
                        autoSize = TextAutoSize.StepBased(maxFontSize = 52.sp),
                        maxLines = 1
                    )
                    Box(
                        modifier = Modifier
                            .size(Dimens.imageLarge)
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
                            .padding(0.1.dp)
                    ) {
                        Image(
                            painter = painterResource(question.questionImage),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(Dimens.imageLarge)
                                .padding(Dimens.small)
                                .clip(RoundedCornerShape(Dimens.small))
                        )
                    }
                    Spacer(modifier = Modifier.padding(Dimens.medium))
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.XXLarge)) {
                        AnswerButton(
                            { viewModel.emit(QuizViewEvent.YesAnswer) },
                            R.drawable.paw_icon,
                            stringResource(R.string.yes)
                        )
                        AnswerButton(
                            { viewModel.emit(QuizViewEvent.NoAnswer) },
                            R.drawable.paw_icon_dark,
                            stringResource(R.string.no)
                        )
                    }
                }
                if (currentUiState.showConfetti) {
                    ConfettiAnimation(
                        onAnimationComplete = {
                            viewModel.emit(QuizViewEvent.ConfettiAnimationComplete)
                        }
                    )
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
            val result = currentUiState.result
            val score = currentUiState.score
            val numberOfQuestions = currentUiState.numberOfQuestions
            if (currentUiState.nameDialogDisplayed) {
                NameInputDialog(
                    nameInput = currentUiState.nameInput,
                    title = currentUiState.nameDialogTitle,
                    onInputChange = {
                        viewModel.emit(QuizViewEvent.NameDialogInput(it))
                    },
                    onDismiss = {
                        viewModel.emit(QuizViewEvent.NameDialogDismiss)
                    },
                    onSave = {
                        viewModel.emit(QuizViewEvent.NameDialogSaveClick(currentUiState.nameInput))
                    }
                )
            }
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = Dimens.large)
                        .statusBarsPadding()
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = result.resultText,
                    style = title,
                    autoSize = TextAutoSize.StepBased(),
                    maxLines = 1
                )
                BasicText(
                    text = stringResource(R.string.score, score, numberOfQuestions),
                    style = subtitle,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 48.sp),
                    maxLines = 1
                )
                Image(
                    painter = painterResource(result.image),
                    contentDescription = result.resultText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(Dimens.imageLarge)
                )
                AnswerButton(
                    navigateToStart,
                    (R.drawable.paw_icon),
                    stringResource(R.string.one_more_time)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameInputDialog(
    nameInput: String,
    title: String,
    onInputChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 8.dp,
            shadowElevation = 8.dp,
            color = White
        ) {
            Column(
                modifier = Modifier.padding(Dimens.XLarge),
                verticalArrangement = Arrangement.spacedBy(Dimens.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = com.umain.basicandroidintegration.ui.theme.title
                )

                Text(
                    text = stringResource(R.string.name_input_dialog_header),
                    style = subtitle
                )

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = onInputChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Pink,
                        unfocusedBorderColor = LightGray
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        Dimens.medium,
                        Alignment.CenterHorizontally
                    )
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.name_input_dialog_dismiss),
                            style = subtitle
                        )
                    }

                    OutlinedButton(
                        onClick = onSave,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            containerColor = Pink,
                            contentColor = White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.name_input_dialog_save),
                            style = subtitle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConfettiAnimation(onAnimationComplete: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = true,
        speed = 2f
    )
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onAnimationComplete()
        }
    }
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun NameInputDialogPreview() {
    BasicAndroidIntegrationTheme {
        NameInputDialog(
            nameInput = "Anna",
            title = "Yay!",
            onInputChange = {},
            onDismiss = {},
            onSave = {}
        )
    }
}
