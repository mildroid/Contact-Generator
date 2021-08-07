package com.mildroid.contactgenerator.domain.model

import com.mildroid.contactgenerator.domain.model.state.WorkerState
import java.util.*

data class WorkerInfo(
    val id: UUID?,
    val state: WorkerState,
    val outputData: String?
) {

    companion object {
        fun emptyWorkerInfo() =
            WorkerInfo(null, WorkerState.IDLE, null)
    }

}