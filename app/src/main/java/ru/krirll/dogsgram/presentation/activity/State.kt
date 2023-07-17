package ru.krirll.dogsgram.presentation.activity

import ru.krirll.dogsgram.domain.model.Dog

sealed class State {
    object Loading: State()
    data class Success(val dogs: List<Dog>): State()
    data class Failure(val message: String): State()
    object Default: State()
}
