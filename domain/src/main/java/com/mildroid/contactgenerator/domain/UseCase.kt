package com.mildroid.contactgenerator.domain

/**
 * Base UseCase.
 */
interface UseCase<in Params> {

    operator fun invoke(params: Params)
}