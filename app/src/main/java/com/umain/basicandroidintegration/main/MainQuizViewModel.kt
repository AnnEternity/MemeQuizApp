package com.umain.basicandroidintegration.main

import com.umain.basicandroidintegration.storage.LeaderBoardStorage
import com.umain.basicandroidintegration.storage.LeaderBoardStorageImpl
import com.umain.revolver.RevolverDefaultErrorHandler
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverViewModel
import kotlinx.coroutines.launch

/**
 * Basic implementation of RevolverViewModel capable of handling events,
 * managing errors, and emitting states.
 */
class MainQuizViewModel(private val leaderBoardStorage: LeaderBoardStorage = LeaderBoardStorageImpl()) :
    RevolverViewModel<MainQuizViewEvent, MainQuizViewState, RevolverEffect>(initialState = MainQuizViewState.Loading) {

    init {
        viewModelScope.launch {
            leaderBoardStorage.scores.collect {scores ->
                emit(MainQuizViewEvent.ScoresRefreshed(scores))
            }
        }

        addEventHandler<MainQuizViewEvent.ViewReady> { event, emit ->
            emit.state(
                MainQuizViewState.Loaded(
                    isButtonOn = false,
                    score = emptyList(),
                )
            )
        }
        addEventHandler<MainQuizViewEvent.ButtonOnClick> { event, emit ->
            val isButtonOn = !event.isClicked
            val loadedState = state.value as MainQuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    isButtonOn = isButtonOn,
                )
            )
        }
        addEventHandler<MainQuizViewEvent.ScoresRefreshed> { event, emit ->
            val score = event.score
            val loadedState = state.value as MainQuizViewState.Loaded
            emit.state(
                loadedState.copy(
                    score = score
                )
            )
        }
        /**
         * Configures the default error handler to emit a custom error state. However,
         * all errors will be mapped to the same state in this case.
         *
         * If you want to map different errors to different states, you need to implement
         * custom error handler, but this topic is not covered in this minimal implementation
         * example.
         */
        addErrorHandler(
            RevolverDefaultErrorHandler(
                MainQuizViewState.Error("Error message")
            )
        )
    }
}
