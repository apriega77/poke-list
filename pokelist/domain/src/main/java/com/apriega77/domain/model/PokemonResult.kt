package com.apriega77.domain.model

sealed class PokemonResult {
    data class Success(val data: List<Pokemon>) : PokemonResult()
    data class Failed(val exception: Exception) : PokemonResult()
}