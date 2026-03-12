package com.umain.basicandroidintegration.main

import com.umain.basicandroidintegration.storage.LeaderBoardStorage
import com.umain.basicandroidintegration.storage.LeaderBoardStorageImpl
import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainQuizViewModel(
    leaderBoardStorage: LeaderBoardStorage = LeaderBoardStorageImpl(),
) : RevolverViewModel<MainQuizViewEvent, MainQuizViewState, RevolverEffect>(initialState = MainQuizViewState.Loading) {
    private val scoresState =
        leaderBoardStorage.scores
            .map { scores ->
                scores.sortedByDescending {
                    if (it.maxPossible == 0) {
                        0.0
                    } else {
                        it.score.toDouble() / it.maxPossible
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )

    init {
        viewModelScope.launch {
            scoresState.collect { scores ->
                emit(MainQuizViewEvent.ScoresRefreshed(scores))
            }
        }

        addEventHandler<MainQuizViewEvent.ViewReady> { event, emit ->
            emit.state(
                MainQuizViewState.Loaded(
                    isButtonOn = false,
                    score = scoresState.value,
                ),
            )
        }
        addEventHandler<MainQuizViewEvent.ButtonOnClick> { event, emit ->
            val isButtonOn = !event.isClicked
            val loadedState = state.value as MainQuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    isButtonOn = isButtonOn,
                ),
            )
        }
        addEventHandler<MainQuizViewEvent.ScoresRefreshed> { event, emit ->
            val score = event.score
            val loadedState = state.value as MainQuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    score = score,
                ),
            )
        }

        addErrorHandler(
            RevolverDefaultErrorHandler(
                MainQuizViewState.Error("Sorry, something went wrong"),
            ),
        )
    }
}
