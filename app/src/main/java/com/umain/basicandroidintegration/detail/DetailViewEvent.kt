package com.umain.basicandroidintegration.detail

import com.umain.revolver.RevolverEvent

/**
 * MainViewEvent represents events triggered by the UI and sent to the ViewModel.
 * When the ViewModel receives an event, it processes one and emits a new state (one of MainViewState).
 */
sealed class DetailViewEvent : RevolverEvent {

    data object ViewReady : DetailViewEvent()
    data object YesAnswer: DetailViewEvent()
    data object NoAnswer: DetailViewEvent()
}
