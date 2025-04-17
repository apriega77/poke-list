package com.apriega77.data.model

data class PokemonDetailResponse(
    val abilities: List<AbilitySlot>
)

data class AbilitySlot(
    val ability: AbilityDetail,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityDetail(
    val name: String,
    val url: String
)
