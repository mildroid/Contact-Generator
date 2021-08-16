package com.mildroid.contactgenerator.domain.model.state

/**
 * Events that MainActivity could sends to ViewModel stands on MVI.
 */
sealed class MainStateEvent() {

    /**
     * Generate contacts with given command.
     */
    data class Generate(val command: String): MainStateEvent()

    /**
     * Help the user.
     */
    data class Help(val msg: String) : MainStateEvent()

    /**
     * Export a .vcf or .csv file contains requested contacts.
     */
    data class Export(val command: String): MainStateEvent()

    /**
     * wtf bro?
     */
    data class NotValid(val command: String): MainStateEvent()

    /**
     * Removes all contacts.
     */
    object Clean : MainStateEvent()

    /**
     * Cancel current working job.
     */
    object Cancel : MainStateEvent()

}