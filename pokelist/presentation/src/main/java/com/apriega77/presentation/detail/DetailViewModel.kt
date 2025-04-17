package com.apriega77.presentation.detail

import androidx.lifecycle.viewModelScope
import com.apriega77.domain.usecase.GetPokemonDetailUseCase
import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getPokemonDetailUseCase: GetPokemonDetailUseCase) :
    BaseViewModel<DetailState, DetailEvent, Unit>() {
    override fun getInitialState(): DetailState {
        return DetailState.Loading
    }

    override fun mapEvent(event: DetailEvent, lastState: DetailState) {
        viewModelScope.launch {
            when (event) {
                is DetailEvent.GetData -> {
                    val result = getPokemonDetailUseCase.invoke(event.name)
                    updateState(DetailState.Loaded(result))
                }
            }
        }

    }
}