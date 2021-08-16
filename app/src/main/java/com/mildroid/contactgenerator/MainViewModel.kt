package com.mildroid.contactgenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mildroid.contactgenerator.domain.CleanerUseCase
import com.mildroid.contactgenerator.domain.GenerateUseCase
import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.None
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import com.mildroid.contactgenerator.domain.model.state.IdleState
import com.mildroid.contactgenerator.domain.model.state.MainStateEvent
import com.mildroid.contactgenerator.domain.model.state.MainViewState
import com.mildroid.contactgenerator.domain.model.state.WorkerState
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
            MainStateEvent.Clean -> {
                cleaner()
                cleanerDetails()
            }
            is MainStateEvent.Generate -> startGenerating(event.command)
            is MainStateEvent.Help -> helper(event.msg)
            is MainStateEvent.NotValid -> _viewState.value =
                MainViewState.Idle(IdleState.Invalid(event.command))
            is MainStateEvent.Export -> TODO()
        }
    }

    private fun cleaner() {
        cleanerUseCase(None())
    }

    private fun helper(msg: String) = when {
        msg.startsWith("--generate") ->
            _viewState.value = MainViewState.Idle(IdleState.GenerateHelp)

        msg == "permission" ->
            _viewState.value = MainViewState.Idle(IdleState.Permission)

        else -> _viewState.value = MainViewState.Idle(IdleState.Help)
    }

    private fun startGenerating(command: String) {
        val rang = command.split(" to ")

        if (numberRangeValidator(rang.first() to rang.last())) {
            generateUseCase(this.generatorParams)
        } else {
            _viewState.value = MainViewState.Idle(IdleState.Invalid(command))
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
//        if (min.size > 5) return false
//        if (min.first() != max.first()) return false
//        if (!min.first().startsWith("+")) return false
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
        cleanerUseCase
            .workerDetails()
            .collect {
                workerStateHandler(it)
            }
    }

}