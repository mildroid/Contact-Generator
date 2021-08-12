package com.mildroid.contactgenerator.domain.model.state

import com.mildroid.contactgenerator.domain.model.WorkerInfo

sealed class MainViewState {

    data class Working(val workerInfo: WorkerInfo) : MainViewState()
    data class Finished(val workerInfo: WorkerInfo): MainViewState()
    data class Canceled(val workerInfo: WorkerInfo): MainViewState()
    data class Idle(val idleState: IdleState): MainViewState()
}

sealed class IdleState {

    object Idle: IdleState()
    object Help: IdleState()
    object GenerateHelp: IdleState()
    data class Invalid(val command: String): IdleState()
}