package com.apriega77.presentation.register

data class RegisterState(
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val enableButton: Boolean = false
)

sealed class RegisterEvent {
    data class Name(val text: String) : RegisterEvent()
    data class Username(val text: String) : RegisterEvent()
    data class Password(val text: String) : RegisterEvent()
    data object Register : RegisterEvent()
    data object NavigateToHome : RegisterEvent()
}

sealed class RegisterEffect {
    data object NavigateToHome : RegisterEffect()
    data class ShowToast(val text: String) : RegisterEffect()
}