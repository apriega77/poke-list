package com.apriega77.data.repository

import android.content.Context
import com.apriega77.data.isInternetAvailable
import com.apriega77.data.local.PokemonDatabaseHelper
import com.apriega77.data.model.PokemonDetailResponse
import com.apriega77.data.remote.PokemonApiService
import com.apriega77.domain.model.Pokemon
import com.apriega77.domain.model.PokemonDetail
import com.apriega77.domain.model.PokemonResult
import com.apriega77.domain.model.request.PokemonRequest
import com.apriega77.domain.repository.PokemonRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApiService,
    private val pokemonDatabaseHelper: PokemonDatabaseHelper,
    @ApplicationContext private val context: Context
) : PokemonRepository {
    override suspend fun getPokemonList(args: PokemonRequest): PokemonResult {
        return if (isInternetAvailable(context)) {
            try {
                val response = api.getPokemonList(args.offset, args.limit)
                val data = response.results.map {
                    Pokemon(it.name)
                }
                pokemonDatabaseHelper.insertPokemonList(data)
                PokemonResult.Success(data)
            } catch (e: Exception) {
                PokemonResult.Failed(e)
            }
        } else {
            val localData = pokemonDatabaseHelper.getPokemonList(args.offset, args.limit)
            if (localData.isNotEmpty()) {
                PokemonResult.Success(localData)
            } else {
                PokemonResult.Failed(Exception("No Internet and no local data"))
            }
        }

    }

    override suspend fun getPokemonDetail(name: String): PokemonDetail {
        val response = api.getPokemonDetail(name)
        return response.toPokemonDetail()
    }

    private fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
        return PokemonDetail(
            ability = this.abilities.map { it.ability.name }
        )
    }
}