package com.mildroid.contactgenerator.domain.model.state

/**
 * Events that MainActivity could sends to ViewModel. stands on MVI.
 */
sealed class MainStateEvent {

    /**
     * Generate contacts with the given range.
     */
    data class Generate(val command: String): MainStateEvent()

    /**
     * Helps the user.
     */
    data class Help(val msg: String) : MainStateEvent()

    /**
     * Export a .vcf or .csv file contains requested contacts.
     */
    data class Export(val command: String): MainStateEvent()

    /**
     * wtf bro?
     */
    data class Error(val error: ErrorState): MainStateEvent()

    /**
     * Removes all contacts.
     */
    object Clean : MainStateEvent()

    /**
     * Cancels current working job.
     */
    object Cancel : MainStateEvent()

}

/**
 * Error State handler.
 */
sealed class ErrorState {

    data class InvalidCommand(var command: String) : ErrorState()
    object PermissionDenied : ErrorState()
}