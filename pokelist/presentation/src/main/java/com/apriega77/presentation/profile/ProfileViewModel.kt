package com.apriega77.presentation.profile

import androidx.lifecycle.viewModelScope
import com.apriega77.domain.model.User
import com.apriega77.domain.usecase.GetProfileUseCase
import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase) :
    BaseViewModel<User, ProfileEvent, Unit>() {
    override fun getInitialState(): User {
        return User("", "")
    }

    override fun mapEvent(event: ProfileEvent, lastState: User) {
        viewModelScope.launch {
            when (event) {
                ProfileEvent.GetProfile -> {
                    val profile = getProfileUseCase.invoke(Unit)
                    updateState(profile)
                }
            }
        }
    }
}