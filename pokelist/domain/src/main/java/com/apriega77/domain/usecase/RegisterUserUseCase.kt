package com.apriega77.domain.usecase

import com.apriega77.domain.model.request.RegisterUser
import com.apriega77.domain.repository.UserRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class RegisterUserUseCase(private val userRepository: UserRepository) :
    BaseUseCase<RegisterUser, Boolean>() {
    override suspend fun build(args: RegisterUser): Boolean {
        return userRepository.registerUser(args)
    }
}
