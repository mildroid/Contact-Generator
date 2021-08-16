package com.mildroid.contactgenerator.domain.model.state

import com.mildroid.contactgenerator.domain.model.WorkerInfo

/**
 * View State for MainActivity stand on MVI.
 */
sealed class MainViewState {

    /**
     * Specifies that a job is working.
     */
    data class Working(val workerInfo: WorkerInfo) : MainViewState()

    /**
     * Working job is finished.
     */
    data class Finished(val workerInfo: WorkerInfo): MainViewState()

    /**
     * Working job is canceled.
     */
    data class Canceled(val workerInfo: WorkerInfo): MainViewState()

    /**
     * There is No working job right now.
     */
    data class Idle(val idleState: IdleState): MainViewState()
}

/**
 * Idle State (?!) to handles the IDLE state on MainActivity.
 */
sealed class IdleState {

    /**
     * Pure IDLE.
     */
    object Idle: IdleState()

    /**
     * Somebody needs help.
     */
    object Help: IdleState()

    /**
     * Somebody need help about generator.
     */
    object GenerateHelp: IdleState()

    /**
     * Contacts Permission is granted.
     */
    object Permission: IdleState()

    /**
     * wtf bro?
     */
    data class Invalid(val command: String): IdleState()
}