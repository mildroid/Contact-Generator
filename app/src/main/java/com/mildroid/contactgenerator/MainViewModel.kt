package com.mildroid.contactgenerator

import androidx.lifecycle.*
import androidx.work.WorkManager
import com.mildroid.contactgenerator.core.log
import com.mildroid.contactgenerator.domain.GenerateUseCase
import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateUseCase: GenerateUseCase

) : ViewModel() {

    private lateinit var generatorParams: GeneratorParams

    internal fun commander(command: String) {
        val commandSplitter = command.trim().split(" ").toMutableList()

        command.log("vm Commander")

        when (commandSplitter.first()) {
            commandSplitter.removeAt(0) -> {
                if (commandSplitter.size < 5) {
//                    notify view to invalid command
                    return
                }

                prepareGenerator(command.removePrefix("generate "))
            }
        }
    }

    private fun prepareGenerator(command: String) {
        val rang = command.split(" to ")
        val (min, max) = rang.first() to rang.last()

        if (numberRangeValidator(min to max)) {
            generateUseCase(this.generatorParams)
        } else {
//            notify view to invalid command
            return
        }

    }

    private fun numberRangeValidator(range: Pair<String, String>): Boolean {
        val min = range.first.split(" ").toMutableList()
        val max = range.second.split(" ").toMutableList()

        if (min.size != max.size) return false

        val actualMin = min.removeLast().toInt()
        val actualMax = max.removeLast().toInt()

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

    fun cancelGenerating() {
        generateUseCase.cancelWork()
    }

    val workerDetails = flow<WorkerInfo> {
        emitAll(
            generateUseCase.workerDetails()
        )
    }

}