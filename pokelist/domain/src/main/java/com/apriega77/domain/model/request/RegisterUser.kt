package com.apriega77.domain.model.request

data class RegisterUser(
    val name: String,
    val username: String,
    val password: String
)