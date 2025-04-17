package com.apriega77.domain.usecase

import com.apriega77.domain.model.request.LoginUser
import com.apriega77.domain.repository.UserRepository
import com.apriega77.domain.usecase.base.BaseUseCase

class LoginUseCase(private val userRepository: UserRepository) :
    BaseUseCase<LoginUser, Boolean>() {
    override suspend fun build(args: LoginUser): Boolean {
        return userRepository.login(args)
    }
}
