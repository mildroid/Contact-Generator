package com.mildroid.contactgenerator.domain.model

/**
 * Params that GeneratorWorker needs to generate contacts.
 */
data class GeneratorParams(
    val min: Int,
    val max: Int,
    val template: String
)