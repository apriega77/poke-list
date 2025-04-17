package com.apriega77.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Event, Effect> : ViewModel() {
    private val initial by lazy { getInitialState() }

    abstract fun getInitialState(): State

    private val _state = MutableStateFlow<State>(initial)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()


    val effect = MutableSharedFlow<Effect>()

    fun updateState(newState: State) {
        _state.update {
            newState
        }
    }

    fun sendEffect(newEffect: Effect) {
        viewModelScope.launch {
            effect.emit(newEffect)
        }
    }

    init {
        _event.onEach {
            mapEvent(it, state.value)
        }.launchIn(viewModelScope)
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected abstract fun mapEvent(event: Event, lastState: State)
}