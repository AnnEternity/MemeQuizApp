package com.umain.basicandroidintegration.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umain.basicandroidintegration.R
import com.umain.basicandroidintegration.ui.theme.Black
import com.umain.basicandroidintegration.ui.theme.Blue
import com.umain.basicandroidintegration.ui.theme.Dimens
import com.umain.basicandroidintegration.ui.theme.LightGray
import com.umain.basicandroidintegration.ui.theme.Orange
import com.umain.basicandroidintegration.ui.theme.Pink
import com.umain.basicandroidintegration.ui.theme.White
import com.umain.basicandroidintegration.ui.theme.Yellow
import com.umain.basicandroidintegration.ui.theme.subtitle

@Composable
fun AnswerButton(onClick: () -> Unit, pawIcon: Int, text: String) {
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
            .padding(0.1.dp)
    ) {
        Button(
            onClick = onClick,
            text = text,
            icon = pawIcon
        )
    }
}

@Composable
private fun Button(onClick: () -> Unit, text: String, icon: Int) {
    OutlinedButton(
        onClick = onClick,
        colors =
            ButtonColors(
                containerColor = White,
                contentColor = Black,
                disabledContainerColor = White,
                disabledContentColor = Black
            ),
        border = BorderStroke(Dimens.border, LightGray)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.small)) {
            Image(
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.cat_s_paw)
            )
            Text(text = text, style = subtitle)
        }
    }
}
