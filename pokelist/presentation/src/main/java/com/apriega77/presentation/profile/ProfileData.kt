package com.apriega77.presentation.profile

sealed class ProfileEvent {
    data object GetProfile : ProfileEvent()
}