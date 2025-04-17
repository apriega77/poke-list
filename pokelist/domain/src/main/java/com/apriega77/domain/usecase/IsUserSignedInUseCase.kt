package com.apriega77.domain.usecase

import com.apriega77.domain.repository.UserRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class IsUserSignedInUseCase(private val userRepository: UserRepository) :
    BaseUseCase<Unit, Boolean>() {
    override suspend fun build(args: Unit): Boolean {
        return userRepository.isUserSignedIn()
    }
}
