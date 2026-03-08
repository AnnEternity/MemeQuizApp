package com.umain.basicandroidintegration.storage

import com.umain.basicandroidintegration.main.QuizTheme

data class Score(
    val name: String,
    val quizTheme: QuizTheme,
    val score: Int,
)
