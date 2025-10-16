package com.umain.basicandroidintegration.detail

import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel

class DetailViewModel : RevolverViewModel<DetailViewEvent, DetailViewState, RevolverEffect>(
    initialState = DetailViewState.Loading
) {
    val listOfQuestions = CatQuizQuestions.entries
    var index: Int = 0
    var rightAnswerCounter = 0

    init {
        addEventHandler<DetailViewEvent.ViewReady> { event, emit ->
            rightAnswerCounter = 0
            index = 0
            emit.state(
                DetailViewState.Loaded(
                    listOfQuestions[index],
                )
            )
        }

        addEventHandler<DetailViewEvent.YesAnswer> { event, emit ->
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
                    DetailViewState.QuizEnd(result)
                )
            } else {
                val loadedState = state.value as DetailViewState.Loaded
                emit.state(
                    loadedState.copy(
                        listOfQuestions[++index],
                    )
                )
            }
        }

        addEventHandler<DetailViewEvent.NoAnswer> { event, emit ->
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
                    DetailViewState.QuizEnd(result)
                )
            } else {
                val loadedState = state.value as DetailViewState.Loaded
                emit.state(
                    loadedState.copy(
                        listOfQuestions[++index],
                    )
                )
            }
        }


        addErrorHandler(
            RevolverDefaultErrorHandler(
                DetailViewState.Error("Error message")
            )
        )
    }


}
