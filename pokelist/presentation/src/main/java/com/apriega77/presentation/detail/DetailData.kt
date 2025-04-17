package com.apriega77.presentation.detail

import com.apriega77.domain.model.PokemonDetail

sealed class DetailState {
    data object Loading : DetailState()
    data class Loaded(val detail: PokemonDetail) : DetailState()
}

sealed class DetailEvent {
    data class GetData(val name : String) : DetailEvent()
}