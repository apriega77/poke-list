package com.apriega77.domain.repository

import com.apriega77.domain.model.PokemonDetail
import com.apriega77.domain.model.PokemonResult
import com.apriega77.domain.model.request.PokemonRequest

interface PokemonRepository {
    suspend fun getPokemonList(args: PokemonRequest): PokemonResult
    suspend fun getPokemonDetail(name: String): PokemonDetail
}