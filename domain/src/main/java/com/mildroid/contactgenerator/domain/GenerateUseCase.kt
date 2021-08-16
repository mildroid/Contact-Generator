package com.mildroid.contactgenerator.domain

import com.mildroid.contactgenerator.domain.model.GeneratorParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Generator useCase that handles operations on [GeneratorWorker]
 */
class GenerateUseCase @Inject constructor(
    private val generator: Generator

) : UseCase<GeneratorParams> {

    override fun invoke(params: GeneratorParams) {
        this.generator.generate(params)
    }

    fun cancelWork() = this.generator.cancel()

    fun workerDetails() = this.generator.workerDetails()
}