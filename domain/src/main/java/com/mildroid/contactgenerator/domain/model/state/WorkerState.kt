package com.mildroid.contactgenerator.domain.model.state

sealed class WorkerState {

    object ENQUEUED: WorkerState()
    object RUNNING: WorkerState()
    object CANCELED: WorkerState()
    object FAILED: WorkerState()
    object BLOCKED: WorkerState()
    object SUCCEEDED: WorkerState()
    object IDLE: WorkerState()
}