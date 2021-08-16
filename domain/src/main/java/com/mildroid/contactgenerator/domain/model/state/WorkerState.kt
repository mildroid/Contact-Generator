package com.mildroid.contactgenerator.domain.model.state

/**
 * Mapper class for WorkInfo state.
 */
sealed class WorkerState {

    /**
     * Used to indicate that the WorkRequest is enqueued and eligible to run.
     */
    object ENQUEUED: WorkerState()

    /**
     * Used to indicate that the WorkerRequest is currently being executed.
     */
    object RUNNING: WorkerState()

    /**
     * Used to indicate that the WorkRequest has been cancelled and will not execute.
     */
    object CANCELED: WorkerState()

    /**
     * Used to indicate that the WorkRequest has completed in a failure state.
     */
    object FAILED: WorkerState()

    /**
     * Used to indicate that the WorkRequest is currently blocked because its 
     * prerequisites haven't finished successfully.
     */
    object BLOCKED: WorkerState()

    /**
     * Used to indicate that the WorkRequest has completed in a successful state.
     */
    object SUCCEEDED: WorkerState()

    /**
     * Used to indicate there is no current working WorkRequest.
     */
    object IDLE: WorkerState()
}