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
import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel

class QuizViewModel(savedStateHandle: SavedStateHandle) :
    RevolverViewModel<QuizViewEvent, QuizViewState, RevolverEffect>(
        initialState = QuizViewState.Loading
    ) {
    val route = savedStateHandle.toRoute<DetailScreen>()
    val theme = route.theme
    val listOfQuestions: List<QuizQuestion> = when (theme) {
        QuizTheme.Cats -> CatQuizQuestions.entries.toList()
        QuizTheme.Dogs -> DogQuizQuestions.entries.toList()
        //QuizTheme.Mixed -> MixedQuizQuestions.entries.toList()
    }
    val listOfResultText: List<QuizResult> = when (theme) {
        QuizTheme.Cats -> CatQuizResult.entries.toList()
        QuizTheme.Dogs -> DogQuizResult.entries.toList()
        //QuizTheme.Mixed -> MixedQuizResult.entries.toList()
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
                )
            )
        }

        addEventHandler<QuizViewEvent.YesAnswer> { event, emit ->
            if (listOfQuestions[index].isAnswerYes) {
                ++rightAnswerCounter
            }
            if (index == listOfQuestions.lastIndex) {
                val result = listOfResultText[rightAnswerCounter]
                emit.state(
                    QuizViewState.QuizEnd(result, rightAnswerCounter, listOfQuestions.size)
                )
            } else {
                val loadedState = state.value as QuizViewState.Loaded
                emit.state(
                    loadedState.copy(
                        listOfQuestions[++index],
                    )
                )
            }
        }

        addEventHandler<QuizViewEvent.NoAnswer> { event, emit ->
            if (!listOfQuestions[index].isAnswerYes) {
                ++rightAnswerCounter
            }
            if (index == listOfQuestions.lastIndex) {
                val result = listOfResultText[rightAnswerCounter]
                emit.state(
                    QuizViewState.QuizEnd(result, rightAnswerCounter, listOfQuestions.size)
                )
            } else {
                val loadedState = state.value as QuizViewState.Loaded
                emit.state(
                    loadedState.copy(
                        listOfQuestions[++index],
                    )
                )
            }
        }

        addErrorHandler(
            RevolverDefaultErrorHandler(
                QuizViewState.Error("Error message")
            )
        )
    }
}
