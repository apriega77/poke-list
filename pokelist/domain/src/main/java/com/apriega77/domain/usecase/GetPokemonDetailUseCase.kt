package com.apriega77.domain.usecase

import com.apriega77.domain.model.PokemonDetail
import com.apriega77.domain.repository.PokemonRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class GetPokemonDetailUseCase(private val pokemonRepository: PokemonRepository) :
    BaseUseCase<String, PokemonDetail>() {
    override suspend fun build(args: String): PokemonDetail {
        return pokemonRepository.getPokemonDetail(args)
    }
}