package com.apriega77.domain.usecase

import com.apriega77.domain.model.PokemonResult
import com.apriega77.domain.model.request.PokemonRequest
import com.apriega77.domain.repository.PokemonRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class GetPokemonListUseCase(private val pokemonRepository: PokemonRepository) :
    BaseUseCase<PokemonRequest, PokemonResult>() {
    override suspend fun build(args: PokemonRequest): PokemonResult {
        return pokemonRepository.getPokemonList(args)
    }
}