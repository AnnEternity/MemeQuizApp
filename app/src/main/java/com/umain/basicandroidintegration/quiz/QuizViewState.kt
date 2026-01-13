package com.umain.basicandroidintegration.quiz

import com.umain.basicandroidintegration.quiz.cat.CatQuizQuestions
import com.umain.basicandroidintegration.quiz.cat.CatQuizResults
import com.umain.revolver.RevolverState

/**
 * MainViewState defines all possible states that the ViewModel can emit to the UI.
 */
sealed class QuizViewState : RevolverState {

    data object Loading : QuizViewState()

    data class Loaded(
        val question: CatQuizQuestions,
        ) : QuizViewState()

    data class QuizEnd(
        val result: CatQuizResults,
        val score: Int,
    ): QuizViewState()

    data class Error(
        val errorMessage: String
    ) : QuizViewState()
}
