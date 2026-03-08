package com.umain.basicandroidintegration.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.umain.basicandroidintegration.DetailScreen
import com.umain.basicandroidintegration.main.QuizTheme
import com.umain.basicandroidintegration.quiz.data.CatQuizQuestions
import com.umain.basicandroidintegration.quiz.data.CatQuizResult
import com.umain.basicandroidintegration.quiz.data.DogQuizQuestions
import com.umain.basicandroidintegration.quiz.data.DogQuizResult
import com.umain.basicandroidintegration.quiz.data.QuizQuestion
import com.umain.basicandroidintegration.quiz.data.QuizResult
import com.umain.basicandroidintegration.quiz.data.isAnswerYes
import com.umain.basicandroidintegration.storage.LeaderBoardStorage
import com.umain.basicandroidintegration.storage.LeaderBoardStorageImpl
import com.umain.basicandroidintegration.storage.Score
import com.umain.revolver.Emitter
import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel

class QuizViewModel(
    savedStateHandle: SavedStateHandle,
) : RevolverViewModel<QuizViewEvent, QuizViewState, RevolverEffect>(
        initialState = QuizViewState.Loading,
    ) {
    private val leaderBoardStorage: LeaderBoardStorage = LeaderBoardStorageImpl()

    val route = savedStateHandle.toRoute<DetailScreen>()
    val theme = route.theme
    val listOfQuestions: List<QuizQuestion> =
        when (theme) {
            QuizTheme.Cats -> CatQuizQuestions.entries.toList()
            QuizTheme.Dogs -> DogQuizQuestions.entries.toList()
            // QuizTheme.Mixed -> MixedQuizQuestions.entries.toList()
        }
    val listOfResultText: List<QuizResult> =
        when (theme) {
            QuizTheme.Cats -> CatQuizResult.entries.toList()
            QuizTheme.Dogs -> DogQuizResult.entries.toList()
            // QuizTheme.Mixed -> MixedQuizResult.entries.toList()
        }
    var index: Int = 0
    var rightAnswerCounter = 0

    init {
        addEventHandler<QuizViewEvent.ViewReady> { event, emit ->
            rightAnswerCounter = 0
            index = 0
            emit.state(
                QuizViewState.Loaded(
                    listOfQuestions[index],
                    theme.name,
                ),
            )
        }

        addEventHandler<QuizViewEvent.YesAnswer> { event, emit ->
            val isAnswerCorrect = listOfQuestions[index].isAnswerYes
            handleAnswer(isAnswerCorrect, emit)
        }

        addEventHandler<QuizViewEvent.NoAnswer> { event, emit ->
            val isAnswerCorrect = !listOfQuestions[index].isAnswerYes
            handleAnswer(isAnswerCorrect, emit)
        }

        addErrorHandler(
            RevolverDefaultErrorHandler(
                QuizViewState.Error("Error message"),
            ),
        )
    }

    private suspend fun handleAnswer(
        isAnswerCorrect: Boolean,
        emit: Emitter<QuizViewState, RevolverEffect>,
    ) {
        if (isAnswerCorrect) {
            ++rightAnswerCounter
        }
        if (index == listOfQuestions.lastIndex) {
            val result = listOfResultText[rightAnswerCounter]
            val newScore =
                Score(
                    score = rightAnswerCounter,
                    quizTheme = theme,
                )
            emit.state(
                QuizViewState.QuizEnd(result, rightAnswerCounter, listOfQuestions.size),
            )
            leaderBoardStorage.save(newScore)
        } else {
            val loadedState = state.value as QuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    question = listOfQuestions[++index],
                ),
            )
        }
    }
}
