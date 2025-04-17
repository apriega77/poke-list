package com.apriega77.presentation.home

import com.apriega77.domain.model.Pokemon

sealed class HomeState {
    data class Success(val data: List<Pokemon>) : HomeState()
    data object Failed : HomeState()
    data object Loading : HomeState()
}

sealed class HomeEvent {
    data class NavigateToDetail(val name: String) : HomeEvent()
    data object GetPokemon : HomeEvent()
}

sealed class HomeEffect{
    data class NavigateToDetail(val name: String) : HomeEffect()
}



