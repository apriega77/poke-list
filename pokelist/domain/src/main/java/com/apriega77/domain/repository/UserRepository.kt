package com.apriega77.domain.repository

import com.apriega77.domain.model.User
import com.apriega77.domain.model.request.LoginUser
import com.apriega77.domain.model.request.RegisterUser

interface UserRepository {
    suspend fun registerUser(args: RegisterUser): Boolean
    suspend fun login(args: LoginUser): Boolean
    suspend fun getProfile(): User
    suspend fun isUserSignedIn(): Boolean
    suspend fun logout(): Boolean
}