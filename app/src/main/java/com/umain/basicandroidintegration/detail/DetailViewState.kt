package com.umain.basicandroidintegration.detail

import com.umain.revolver.RevolverState

/**
 * MainViewState defines all possible states that the ViewModel can emit to the UI.
 */
sealed class DetailViewState : RevolverState {

    data object Loading : DetailViewState()

    data class Loaded(
        val question: CatQuizQuestions,
        ) : DetailViewState()

    data class QuizEnd(
        val result: CatQuizResults,
    ): DetailViewState()

    data class Error(
        val errorMessage: String
    ) : DetailViewState()
}
