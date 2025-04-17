package com.apriega77.presentation.login


data class LoginState(val username: String = "", val password: String = "")

sealed class LoginEvent {
    data class Username(val text: String) : LoginEvent()
    data class Password(val text: String) : LoginEvent()
    data object Login : LoginEvent()
    data object NavigateToRegister : LoginEvent()
    data object CheckIsUserSignedInUseCase : LoginEvent()
}

sealed class LoginEffect {
    data object NavigateToRegister : LoginEffect()
    data object NavigateToHome : LoginEffect()
    data class ShowToast(val text: String) : LoginEffect()
}