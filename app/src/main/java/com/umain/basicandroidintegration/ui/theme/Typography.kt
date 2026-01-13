package com.umain.basicandroidintegration.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.umain.basicandroidintegration.R

val new_amsterdam = FontFamily(Font(R.font.new_amsterdam_regular))
val jura = FontFamily(Font(R.font.jura_regular))
val title1 = TextStyle(
    fontFamily = new_amsterdam,
    fontWeight = FontWeight.W400,
    fontSize = 52.sp,
    lineHeight = 52.sp,
    letterSpacing = 0.sp,
    color = Color.Black,
)

val subtitle = TextStyle(
    fontFamily = jura,
    fontWeight = FontWeight.W400,
    fontSize = 18.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
)

val text = TextStyle(
    fontFamily = jura,
    fontWeight = FontWeight.W500,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
)