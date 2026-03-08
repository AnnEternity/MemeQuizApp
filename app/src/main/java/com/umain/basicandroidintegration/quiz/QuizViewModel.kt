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

        addEventHandler<QuizViewEvent.NameDialogSaveClick> { event, emit ->
            dismissNameDialog(emit)

            val newScore =
                Score(
                    score = rightAnswerCounter,
                    quizTheme = theme,
                    name = event.finalName,
                    maxPossible = listOfQuestions.size,
                )

            leaderBoardStorage.save(newScore)
        }

        addEventHandler<QuizViewEvent.NameDialogInput> { event, emit ->
            updateNameInput(emit, event.name)
        }

        addEventHandler<QuizViewEvent.NameDialogDismiss> { event, emit ->
            dismissNameDialog(emit)
        }

        addEventHandler<QuizViewEvent.ConfettiAnimationComplete> { event, emit ->
            emitNextQuizState(emit)
        }

        addErrorHandler(
            RevolverDefaultErrorHandler(
                QuizViewState.Error("Error message"),
            ),
        )
    }

    private fun updateNameInput(
        emit: Emitter<QuizViewState, RevolverEffect>,
        nameInput: String,
    ) {
        val currentState = state.value
        if (currentState !is QuizViewState.QuizEnd) {
            return
        }
        val newState = currentState.copy(nameInput = nameInput)
        emit.state(newState)
    }

    private fun dismissNameDialog(emit: Emitter<QuizViewState, RevolverEffect>) {
        val currentState = state.value
        if (currentState !is QuizViewState.QuizEnd) {
            return
        }
        val newState = currentState.copy(nameDialogDisplayed = false)
        emit.state(newState)
    }

    private fun handleAnswer(
        isAnswerCorrect: Boolean,
        emit: Emitter<QuizViewState, RevolverEffect>,
    ) {
        if (isAnswerCorrect) {
            ++rightAnswerCounter
            val loadedState = state.value as QuizViewState.Loaded
            emit.state(loadedState.copy(showConfetti = true))
        } else {
            emitNextQuizState(emit)
        }
    }

    private fun emitNextQuizState(emit: Emitter<QuizViewState, RevolverEffect>) {
        if (index == listOfQuestions.lastIndex) {
            val result = listOfResultText[rightAnswerCounter]
            emit.state(
                QuizViewState.QuizEnd(
                    result = result,
                    score = rightAnswerCounter,
                    numberOfQuestions = listOfQuestions.size,
                    nameDialogDisplayed = true,
                    nameInput = "",
                ),
            )
        } else {
            val loadedState = state.value as QuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    question = listOfQuestions[++index],
                    showConfetti = false,
                ),
            )
        }
    }
}
