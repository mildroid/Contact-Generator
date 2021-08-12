package com.mildroid.contactgenerator.domain.model.state

sealed class MainStateEvent() {

    data class Generate(val command: String): MainStateEvent()
    data class Help(val msg: String) : MainStateEvent()
    data class Export(val command: String): MainStateEvent()
    data class NotValid(val command: String): MainStateEvent()
    object Clean : MainStateEvent()
    object Cancel : MainStateEvent()

}