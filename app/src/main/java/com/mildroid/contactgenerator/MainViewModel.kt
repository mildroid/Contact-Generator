package com.mildroid.contactgenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mildroid.contactgenerator.domain.CleanerUseCase
import com.mildroid.contactgenerator.domain.GenerateUseCase
import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.None
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import com.mildroid.contactgenerator.domain.model.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MainViewModel stands on LifeCycle components.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateUseCase: GenerateUseCase,
    private val cleanerUseCase: CleanerUseCase

) : ViewModel() {

    private lateinit var generatorParams: GeneratorParams

    private val _viewState =
        MutableStateFlow<MainViewState>(MainViewState.Idle(IdleState.Idle))

    val viewState: StateFlow<MainViewState>
        get() = _viewState

    init {
        viewModelScope.launch {
            generateUseCase
                .workerDetails()
                .collect {
                    workerStateHandler(it)
                }
        }
    }

    private fun workerStateHandler(workerInfo: WorkerInfo) {
        _viewState.value = when (workerInfo.state) {
            WorkerState.CANCELED, WorkerState.FAILED -> MainViewState.Canceled(workerInfo)
            WorkerState.RUNNING, WorkerState.ENQUEUED -> MainViewState.Working(workerInfo)
            WorkerState.SUCCEEDED -> MainViewState.Finished(workerInfo)
            WorkerState.IDLE -> MainViewState.Idle(IdleState.Idle)
            WorkerState.BLOCKED -> TODO() /* for now this never happens :) */
        }
    }

    internal fun onEvent(event: MainStateEvent) {
        when (event) {
            MainStateEvent.Cancel -> cancelGenerating()
            MainStateEvent.Clean -> cleanerDetails()
            is MainStateEvent.Generate -> startGenerating(event.command)
            is MainStateEvent.Help -> helper(event.msg)
            is MainStateEvent.Error -> errorHandler(event.error)
            is MainStateEvent.Export -> TODO() /* Not Implemented yet */
        }
    }

    private fun errorHandler(error: ErrorState) {
        _viewState.value = when (error) {
            is ErrorState.InvalidCommand -> MainViewState.Idle(IdleState.Invalid(error.command))
            ErrorState.PermissionDenied ->  MainViewState.Idle(IdleState.Permission)
        }
    }

    private fun cleaner() {
        cleanerUseCase(None())
    }

    private fun helper(msg: String) = when {
        msg.startsWith("--generate") ->
            _viewState.value = MainViewState.Idle(IdleState.GenerateHelp)

        else -> _viewState.value = MainViewState.Idle(IdleState.Help)
    }

    private fun startGenerating(command: String) {

        fun invalidCommand() {
            _viewState.value = MainViewState.Idle(IdleState.Invalid(command))
        }

        val rang = command
            .split(" to ")
            .also {
                if (it.size < 2) invalidCommand()
            }

        if (numberRangeValidator(rang.first() to rang.last())) {
            generateUseCase(this.generatorParams)
        } else {
            invalidCommand()
        }

    }

    private fun numberRangeValidator(range: Pair<String, String>): Boolean {
        val min = range.first.split(" ").toMutableList()
        val max = range.second.split(" ").toMutableList()

        if (min.size != max.size) return false
        if (min.size < 2) return false

        val actualMin = min.removeLast().toInt()
        val actualMax = max.removeLast().toInt()

        if (actualMin.toString().length != actualMax.toString().length) return false
        if (min.joinToString() != max.joinToString()) return false
        if (!min.first().startsWith("+")) return false
        if (min.size == 1) return false

        this.generatorParams =
            GeneratorParams(
                actualMin,
                actualMax,
                min.joinToString(" ")
            )

        return true
    }

    private fun cancelGenerating() {
        generateUseCase.cancelWork()
    }

    private fun cleanerDetails() = viewModelScope.launch {
        cleaner()

        cleanerUseCase
            .workerDetails()
            .collect {
                workerStateHandler(it)
            }
    }

}