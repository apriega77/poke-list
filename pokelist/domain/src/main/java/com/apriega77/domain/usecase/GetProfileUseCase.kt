package com.apriega77.domain.usecase

import com.apriega77.domain.model.User
import com.apriega77.domain.repository.UserRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class GetProfileUseCase(private val userRepository: UserRepository) :
    BaseUseCase<Unit, User>() {
    override suspend fun build(args: Unit): User {
        return userRepository.getProfile()
    }
}
