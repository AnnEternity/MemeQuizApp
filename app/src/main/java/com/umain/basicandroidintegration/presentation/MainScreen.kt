package com.umain.basicandroidintegration.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.presentation.MainViewEvent.ButtonOnClick
import com.umain.basicandroidintegration.ui.theme.subtitle
import com.umain.basicandroidintegration.ui.theme.title1
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    onNavigate: () -> Unit
) {
    // Local UI state that tracks ViewModel's current state
    val uiState = remember { mutableStateOf(viewModel.state.value) }

    LaunchedEffect(key1 = true) {

        viewModel.emit(MainViewEvent.ViewReady)

        launch {
            viewModel.state.collect {
                uiState.value = it
            }
        }
    }
    when (uiState.value) {
        is MainViewState.Error -> {
            val value = uiState.value as MainViewState.Error
            Text(
                text = value.errorMessage,
                color = Color.Red,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 100.dp)
            )
        }

        is MainViewState.Loading -> {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Main screen
        is MainViewState.Loaded -> {
            val value = uiState.value as MainViewState.Loaded
            var clickedButton = value.isButtonOn
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = "Cats",
                    style = title1,
                    autoSize = TextAutoSize.StepBased(),
                    modifier = Modifier,
                    overflow = TextOverflow.Clip,
                )
                BasicText(
                    text = value.welcomeMessage,
                    autoSize = TextAutoSize.StepBased(maxFontSize = 32.sp),
                    maxLines = 8,
                )
                OutlinedButton(
                    onClick = {
                        viewModel.emit(ButtonOnClick(clickedButton))
                    },
                    colors = ButtonColors(
                        containerColor = if (value.isButtonOn) Color.White else Color.Gray,
                        contentColor = if (value.isButtonOn) Color.Black else Color.White,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    ),
                    border = BorderStroke(0.5.dp, Color.LightGray)
                ) {
                    Text(
                        text = if (value.isButtonOn) "Meaw" else "Click me",
                        style = subtitle
                    )
                }
                AnimatedVisibility(value.isButtonOn) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.funny_cat_image),
                            contentDescription = "Funny cat",
                            modifier = Modifier
                                .animateEnterExit()
                                .fillMaxWidth()
                        )
                        OutlinedButton(
                            onClick = onNavigate,
                            colors = ButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.Black
                            ),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            modifier = Modifier.animateEnterExit()
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.paw_icon),
                                    contentDescription = "Cat's paw"
                                )
                                Text(text = "Start quiz", style = subtitle)
                            }

                        }
                    }

                }
            }
        }
    }
}
