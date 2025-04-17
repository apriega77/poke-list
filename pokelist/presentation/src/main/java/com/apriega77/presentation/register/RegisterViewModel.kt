package com.apriega77.presentation.register

import androidx.lifecycle.viewModelScope
import com.apriega77.domain.model.request.LoginUser
import com.apriega77.domain.model.request.RegisterUser
import com.apriega77.domain.usecase.LoginUseCase
import com.apriega77.domain.usecase.RegisterUserUseCase
import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUseCase: LoginUseCase
) :
    BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>() {
    override fun getInitialState(): RegisterState {
        return RegisterState()
    }

    override fun mapEvent(event: RegisterEvent, lastState: RegisterState) {
        viewModelScope.launch {
            when (event) {
                is RegisterEvent.Name -> {
                    updateState(
                        lastState.copy(
                            name = event.text,
                            enableButton = checkButtonState(lastState)
                        )
                    )
                }

                RegisterEvent.NavigateToHome -> {
                    sendEffect(RegisterEffect.NavigateToHome)
                }

                is RegisterEvent.Password -> {
                    updateState(
                        lastState.copy(
                            password = event.text,
                            enableButton = checkButtonState(lastState)
                        )
                    )
                }

                RegisterEvent.Register -> {
                    val isSuccessRegister = registerUserUseCase(
                        RegisterUser(
                            name = lastState.name,
                            username = lastState.username,
                            password = lastState.password
                        )
                    )

                    if (isSuccessRegister) {
                        val isSuccessLogin = loginUseCase(
                            LoginUser(
                                username = lastState.username,
                                password = lastState.password
                            )
                        )

                        if (isSuccessLogin) {
                            sendEffect(RegisterEffect.NavigateToHome)
                        } else {
                            sendEffect(RegisterEffect.ShowToast("Login Failed"))
                        }
                    } else {
                        sendEffect(RegisterEffect.ShowToast("Register Failed"))
                    }
                }

                is RegisterEvent.Username -> {
                    updateState(
                        lastState.copy(
                            username = event.text,
                            enableButton = checkButtonState(lastState)
                        )
                    )
                }
            }
        }

    }

    private fun checkButtonState(lastState: RegisterState): Boolean {
        return lastState.name.isNotEmpty() && lastState.username.isNotEmpty() && lastState.password.isNotEmpty()
    }
}