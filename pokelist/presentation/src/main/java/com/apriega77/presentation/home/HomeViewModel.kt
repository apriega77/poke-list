package com.apriega77.presentation.home

import androidx.lifecycle.viewModelScope
import com.apriega77.domain.model.PokemonResult
import com.apriega77.domain.model.request.PokemonRequest
import com.apriega77.domain.usecase.GetPokemonListUseCase
import com.apriega77.domain.usecase.LogoutUseCase
import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val logoutUseCase: LogoutUseCase
) :
    BaseViewModel<HomeState, HomeEvent, HomeEffect>() {
    override fun getInitialState(): HomeState {
        return HomeState.Loading
    }

    private var offset = 0
    private val limit = 10

    override fun mapEvent(event: HomeEvent, lastState: HomeState) {
        viewModelScope.launch {
            when (event) {
                HomeEvent.GetPokemon -> {
                    val result =
                        getPokemonListUseCase.invoke(PokemonRequest(offset = offset, limit = limit))
                    when (result) {
                        is PokemonResult.Failed -> {
                            updateState(HomeState.Failed)
                        }

                        is PokemonResult.Success -> {
                            val currentList = (lastState as? HomeState.Success)?.data.orEmpty()
                            val updatedList = currentList + result.data
                            updateState(HomeState.Success(updatedList))
                            offset += limit
                        }
                    }

                }

                is HomeEvent.NavigateToDetail -> {
                    sendEffect(HomeEffect.NavigateToDetail(event.name))
                }

                HomeEvent.Logout -> {
                    val isSuccess = logoutUseCase.invoke(Unit)
                    if (isSuccess) sendEffect(HomeEffect.NavigateToLogin)
                }
            }
        }

    }
}