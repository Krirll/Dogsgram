package ru.krirll.dogsgram.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.krirll.dogsgram.domain.model.Dog
import ru.krirll.dogsgram.domain.use_cases.GetDogsUseCase
import ru.krirll.dogsgram.presentation.activity.State

class MainViewModel(
    private val getDogsUseCase: GetDogsUseCase
) : ViewModel() {

    init {
        getDogs()
    }

    private var _state = MutableStateFlow<State>(State.Default)
    val state: StateFlow<State> = _state

    private val list: MutableList<Dog> = mutableListOf()

    fun getDogs() {
        viewModelScope.launch {
            getDogsUseCase.invoke()
                .onStart {
                    _state.emit(State.Loading)
                }
                .onEmpty {
                    _state.emit(State.Failure("empty result"))
                }
                .catch {it ->
                    _state.emit(State.Failure(it.message!!))
                }
                .collect {
                    list.addAll(it)
                    _state.emit(State.Success(list))
                }
        }
    }
}