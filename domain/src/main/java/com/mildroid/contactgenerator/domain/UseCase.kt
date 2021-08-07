package com.mildroid.contactgenerator.domain

interface UseCase<in Params> {

    operator fun invoke(params: Params)
}