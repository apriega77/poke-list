package com.apriega77.presentation.login

import androidx.lifecycle.viewModelScope
import com.apriega77.domain.model.request.LoginUser
import com.apriega77.domain.usecase.IsUserSignedInUseCase
import com.apriega77.domain.usecase.LoginUseCase
import com.apriega77.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isUserSignedInUseCase: IsUserSignedInUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>() {
    override fun getInitialState(): LoginState {
        return LoginState()
    }

    override fun mapEvent(event: LoginEvent, lastState: LoginState) {
        viewModelScope.launch {
            when (event) {
                LoginEvent.Login -> {
                    val isSuccess = loginUseCase.invoke(
                        LoginUser(
                            username = state.value.username,
                            password = state.value.password
                        )
                    )

                    if (isSuccess) sendEffect(LoginEffect.NavigateToHome) else sendEffect(
                        LoginEffect.ShowToast("Login Failed")
                    )

                }

                LoginEvent.NavigateToRegister -> {
                    sendEffect(LoginEffect.NavigateToRegister)
                }

                is LoginEvent.Password -> {
                    updateState(lastState.copy(password = event.text))
                }

                is LoginEvent.Username -> {
                    updateState(lastState.copy(username = event.text))
                }

                LoginEvent.CheckIsUserSignedInUseCase -> {
                    val isSignedIn = isUserSignedInUseCase.invoke(Unit)
                    delay(300)
                    if (isSignedIn) sendEffect(LoginEffect.NavigateToHome)
                }
            }
        }
    }
}