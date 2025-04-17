package com.apriega77.presentation

import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor() :
    BaseViewModel<PokeListState, PokeListEvent, PokeListEffect>() {
    override fun getInitialState(): PokeListState {
        return PokeListState("") { ToolBarState.Login }
    }

    override fun mapEvent(event: PokeListEvent, lastState: PokeListState) {
        when (event) {
            is PokeListEvent.NavigateToDetail -> {
                updateState(newState = lastState.copy(name = event.name))
                sendEffect(PokeListEffect.NavigateToDetail)
            }

            PokeListEvent.NavigateToHome -> {
                sendEffect(PokeListEffect.NavigateToHome)
            }

            PokeListEvent.NavigateToProfile -> {
                sendEffect(PokeListEffect.NavigateToProfile)
            }

            PokeListEvent.NavigateToRegister -> {
                sendEffect(PokeListEffect.NavigateToRegister)
            }

            is PokeListEvent.SetToolBarState -> {
                updateState(lastState.copy(toolBarState = event.toolBarState))
            }

            is PokeListEvent.ShowToast -> {
                sendEffect(PokeListEffect.ShowToast(event.text))
            }
        }
    }
}