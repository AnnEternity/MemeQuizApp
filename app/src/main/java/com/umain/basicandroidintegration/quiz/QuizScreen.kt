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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.quiz.data.image
import com.umain.basicandroidintegration.quiz.data.questionImage
import com.umain.basicandroidintegration.quiz.data.questionText
import com.umain.basicandroidintegration.quiz.data.resultText
import com.umain.basicandroidintegration.ui.theme.BasicAndroidIntegrationTheme
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.title1

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(),
    navigateToStart: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.emit(QuizViewEvent.ViewReady)
    }

    when (val currentUiState = uiState) {
        is QuizViewState.Error -> {
            Text(
                text = currentUiState.errorMessage,
                color = Color.Red,
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 100.dp),
            )
        }

        is QuizViewState.Loaded -> {
            val question = currentUiState.question
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = 32.dp)
                        .statusBarsPadding()
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BasicText(
                    text = "${currentUiState.themeText} quiz",
                    style = title1,
                    autoSize = TextAutoSize.StepBased(),
                    maxLines = 1,
                )
                BasicText(
                    text = question.questionText,
                    style = subtitle,
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
                    Button(
                        { viewModel.emit(QuizViewEvent.YesAnswer) },
                        "yes",
                        R.drawable.paw_icon,
                    )
                    Button(
                        { viewModel.emit(QuizViewEvent.NoAnswer) },
                        "no",
                        R.drawable.paw_icon_dark,
                    )
                }
            }
        }

        is QuizViewState.Loading -> {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
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
                    onInputChange = {
                        viewModel.emit(QuizViewEvent.NameDialogInput(it))
                    },
                    onDismiss = {
                        viewModel.emit(QuizViewEvent.NameDialogDismiss)
                    },
                    onSave = {
                        viewModel.emit(QuizViewEvent.NameDialogSaveClick(currentUiState.nameInput))
                    },
                )
            }
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .statusBarsPadding()
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BasicText(
                    text = result.resultText,
                    style = title1,
                    autoSize = TextAutoSize.StepBased(),
                    maxLines = 1,
                )
                BasicText(
                    text = stringResource(R.string.score, score, numberOfQuestions),
                    style = subtitle,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 48.sp),
                    maxLines = 1,
                )
                Image(
                    painter = painterResource(result.image),
                    contentDescription = result.resultText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(350.dp),
                )
                Button(navigateToStart, "One more time?", R.drawable.paw_icon)
            }
        }
    }
}

@Composable
private fun Button(
    onClick: () -> Unit,
    text: String,
    icon: Int,
) {
    OutlinedButton(
        onClick = onClick,
        colors =
            ButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black,
            ),
        border = BorderStroke(0.5.dp, Color.LightGray),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(
                painter = painterResource(icon),
                contentDescription = "Cat's paw",
            )
            Text(text = text, style = subtitle)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameInputDialog(
    nameInput: String,
    onInputChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Surface {
            Column(
                modifier =
                    Modifier
                        .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.name_input_dialog_header),
                )

                OutlinedTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    value = nameInput,
                    onValueChange = onInputChange,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                ) {
                    TextButton(
                        onClick = onDismiss,
                    ) {
                        Text(
                            text = stringResource(R.string.name_input_dialog_dismiss),
                        )
                    }

                    TextButton(
                        onClick = onSave,
                    ) {
                        Text(
                            text = stringResource(R.string.name_input_dialog_save),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NameInputDialogPreview() {
    BasicAndroidIntegrationTheme {
        NameInputDialog(
            nameInput = "Anna",
            onInputChange = {},
            onDismiss = {},
            onSave = {},
        )
    }
}
