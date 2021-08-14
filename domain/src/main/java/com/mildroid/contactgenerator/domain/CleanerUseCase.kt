package com.mildroid.contactgenerator.domain

import com.mildroid.contactgenerator.domain.model.None
import javax.inject.Inject

class CleanerUseCase @Inject constructor(
    private val cleaner: Cleaner

): UseCase<None>{

    override fun invoke(params: None) {
        cleaner.clean()
    }

    fun workerDetails() = cleaner.workerDetails()
}