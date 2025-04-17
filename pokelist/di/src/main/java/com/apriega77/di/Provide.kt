package com.apriega77.di

import com.apriega77.domain.repository.PokemonRepository
import com.apriega77.domain.repository.UserRepository
import com.apriega77.domain.usecase.GetPokemonDetailUseCase
import com.apriega77.domain.usecase.GetPokemonListUseCase
import com.apriega77.domain.usecase.GetProfileUseCase
import com.apriega77.domain.usecase.IsUserSignedInUseCase
import com.apriega77.domain.usecase.LoginUseCase
import com.apriega77.domain.usecase.LogoutUseCase
import com.apriega77.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provide {
    @Provides
    @Singleton
    fun provideRegisterUseCase(userRepository: UserRepository) = RegisterUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideLoginUseCase(userRepository: UserRepository) = LoginUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetProfileUseCase(userRepository: UserRepository) = GetProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideIsUserSignedInUseCase(userRepository: UserRepository) =
        IsUserSignedInUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetPokemonDetailUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonDetailUseCase(pokemonRepository)

    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonListUseCase(pokemonRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(userRepository: UserRepository) = LogoutUseCase(userRepository)
}
