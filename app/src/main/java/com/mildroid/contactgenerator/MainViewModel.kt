package com.mildroid.contactgenerator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.mildroid.contactgenerator.core.length
import com.mildroid.contactgenerator.core.log
import com.mildroid.contactgenerator.domain.GenerateUseCase
import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.state.IdleState
import com.mildroid.contactgenerator.domain.model.state.MainStateEvent
import com.mildroid.contactgenerator.domain.model.state.MainViewState
import com.mildroid.contactgenerator.domain.model.state.WorkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateUseCase: GenerateUseCase

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
                    _viewState.value  = when (it.state) {
                        WorkerState.CANCELED, WorkerState.FAILED -> MainViewState.Canceled(it)
                        WorkerState.RUNNING, WorkerState.ENQUEUED -> MainViewState.Working(it)
                        WorkerState.SUCCEEDED -> MainViewState.Finished(it)
                        WorkerState.IDLE -> MainViewState.Idle(IdleState.Idle)
                        WorkerState.BLOCKED -> TODO() /* for now this never happens :) */
                    }
                }
        }
    }

    internal fun onEvent(event: MainStateEvent) {
        when (event) {
            MainStateEvent.Cancel -> cancelGenerating()
            MainStateEvent.Clean -> TODO()
            is MainStateEvent.Generate -> startGenerating(event.command)
            is MainStateEvent.Help -> helper(event.msg)
            is MainStateEvent.NotValid -> _viewState.value = MainViewState.Idle(IdleState.Invalid(event.command))
            is MainStateEvent.Export -> TODO()
        }
    }

    private fun helper(msg: String) = when {
        msg.startsWith("--generate") ->
            _viewState.value = MainViewState.Idle(IdleState.GenerateHelp)

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

    private fun cancelGenerating() = viewModelScope.launch {
        generateUseCase.cancelWork()
    }

}