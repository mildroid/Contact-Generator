package com.mildroid.contactgenerator

import androidx.lifecycle.ViewModel
import com.mildroid.contactgenerator.domain.GenerateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateUseCase: GenerateUseCase
) : ViewModel() {

    fun generate() {
        generateUseCase.invoke()
    }
}