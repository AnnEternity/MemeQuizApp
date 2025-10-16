package com.umain.basicandroidintegration.quiz

import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel

class QuizViewModel : RevolverViewModel<QuizViewEvent, QuizViewState, RevolverEffect>(
    initialState = QuizViewState.Loading
) {
    val listOfQuestions = CatQuizQuestions.entries
    var index: Int = 0
    var rightAnswerCounter = 0

    init {
        addEventHandler<QuizViewEvent.ViewReady> { event, emit ->
            rightAnswerCounter = 0
            index = 0
            emit.state(
                QuizViewState.Loaded(
                    listOfQuestions[index],
                )
            )
        }

        addEventHandler<QuizViewEvent.YesAnswer> { event, emit ->
            when (listOfQuestions.get(index)) {
                CatQuizQuestions.POP,
                CatQuizQuestions.MAX,
                CatQuizQuestions.HAPPY,
                CatQuizQuestions.BBB -> ++rightAnswerCounter

                else -> Unit
            }
            if (index == listOfQuestions.lastIndex) {
                val result = when (rightAnswerCounter) {
                    0 -> CatQuizResults.NotACat
                    1 -> CatQuizResults.LittleBit
                    2 -> CatQuizResults.MediumCat
                    3 -> CatQuizResults.BetterThanHalf
                    4 -> CatQuizResults.BigCat
                    else -> CatQuizResults.Best
                }
                emit.state(
                    QuizViewState.QuizEnd(result, rightAnswerCounter)
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
            when (listOfQuestions.get(index)) {
                CatQuizQuestions.HUH,
                CatQuizQuestions.CHIPICHAPA -> ++rightAnswerCounter
                else -> Unit
            }
            if (index == listOfQuestions.lastIndex) {
                val result = when (rightAnswerCounter) {
                    0 -> CatQuizResults.NotACat
                    1 -> CatQuizResults.LittleBit
                    2 -> CatQuizResults.LittleBit
                    3 -> CatQuizResults.MediumCat
                    4 -> CatQuizResults.BetterThanHalf
                    5 -> CatQuizResults.BigCat
                    else -> CatQuizResults.Best
                }
                emit.state(
                    QuizViewState.QuizEnd(result, rightAnswerCounter)
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
