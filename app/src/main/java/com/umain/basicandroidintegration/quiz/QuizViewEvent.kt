package com.umain.basicandroidintegration.quiz

import com.umain.revolver.RevolverEvent

/**
 * MainViewEvent represents events triggered by the UI and sent to the ViewModel.
 * When the ViewModel receives an event, it processes one and emits a new state (one of MainViewState).
 */
sealed class QuizViewEvent : RevolverEvent {
    data object ViewReady : QuizViewEvent()

    data object YesAnswer : QuizViewEvent()

    data object NoAnswer : QuizViewEvent()

    data object NameDialogDismiss : QuizViewEvent()

    data class NameDialogInput(
        val name: String,
    ) : QuizViewEvent()

    data class NameDialogSaveClick(
        val finalName: String,
    ) : QuizViewEvent()

    data object ConfettiAnimationComplete : QuizViewEvent()
}
