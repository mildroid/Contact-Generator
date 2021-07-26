package com.mildroid.contactgenerator.domain

import javax.inject.Inject

class GenerateUseCase @Inject constructor(
    private val generator: Generator

): UseCase {

    override fun invoke() {
        this.generator.generate("data - invoked")
    }
}

interface Generator {

    fun generate(data: String)
}