package com.umain.basicandroidintegration.quiz

import com.umain.basicandroidintegration.quiz.data.QuizQuestion
import com.umain.basicandroidintegration.quiz.data.QuizResult
import com.umain.revolver.RevolverState

/**
 * MainViewState defines all possible states that the ViewModel can emit to the UI.
 */
sealed class QuizViewState : RevolverState {

    data object Loading : QuizViewState()

    data class Loaded(
        val question: QuizQuestion,
        val themeText: String,
        ) : QuizViewState()

    data class QuizEnd(
        val result: QuizResult,
        val score: Int,
        val numberOfQuestions: Int,
    ): QuizViewState()

    data class Error(
        val errorMessage: String
    ) : QuizViewState()
}
