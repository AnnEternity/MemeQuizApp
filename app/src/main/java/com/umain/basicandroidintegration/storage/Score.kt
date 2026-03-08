package com.umain.basicandroidintegration.storage

import com.umain.basicandroidintegration.main.QuizTheme

data class Score(
    val name: String = "",
    val quizTheme: QuizTheme = QuizTheme.Cats,
    val score: Int = 0,
    val maxPossible: Int = 0,
)
