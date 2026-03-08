package com.umain.basicandroidintegration.main

import com.umain.basicandroidintegration.storage.Score
import com.umain.revolver.RevolverEvent

/**
 * MainViewEvent represents events triggered by the UI and sent to the ViewModel.
 * When the ViewModel receives an event, it processes one and emits a new state (one of MainViewState).
 */
sealed class MainQuizViewEvent : RevolverEvent {

    /**
     * Pass parameters as needed, but keep them minimal.
     * Offloading decision-making logic from the UI to the ViewModel or repositories
     * simplifies multi-platform integration. Add complexity only if necessary.
     */
    data object ViewReady : MainQuizViewEvent()
    data class ButtonOnClick( val isClicked: Boolean) : MainQuizViewEvent()
    data class ScoresRefreshed(val score: List<Score>) : MainQuizViewEvent()
}
