package com.apriega77.data.repository

import com.apriega77.data.local.SessionManager
import com.apriega77.data.local.UserDatabaseHelper
import com.apriega77.domain.model.User
import com.apriega77.domain.model.request.LoginUser
import com.apriega77.domain.model.request.RegisterUser
import com.apriega77.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDatabaseHelper: UserDatabaseHelper,
    private val sessionManager: SessionManager
) :
    UserRepository {
    override suspend fun registerUser(args: RegisterUser): Boolean {
        return userDatabaseHelper.registerUser(
            name = args.name,
            username = args.username,
            password = args.password
        )
    }

    override suspend fun login(args: LoginUser): Boolean {
        val login = userDatabaseHelper.login(username = args.username, password = args.password)
        sessionManager.setSignedIn(args.username)
        return login
    }

    override suspend fun getProfile(): User {
        val username = sessionManager.getSignedInUser() ?: ""
        return userDatabaseHelper.getUserByUsername(username) ?: User("", "")
    }

    override suspend fun  isUserSignedIn(): Boolean {
        return sessionManager.isUserSignedIn()
    }

    override suspend fun logout(): Boolean {
        return sessionManager.signOut()
    }
}