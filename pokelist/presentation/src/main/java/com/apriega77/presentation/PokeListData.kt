package com.apriega77.presentation

data class PokeListState(val name: String, val toolBarState: () -> ToolBarState)

sealed class PokeListEvent {
    data object NavigateToRegister : PokeListEvent()
    data object NavigateToHome : PokeListEvent()
    data class NavigateToDetail(val name: String) : PokeListEvent()
    data object NavigateToProfile : PokeListEvent()
    data class SetToolBarState(val toolBarState: () -> ToolBarState) : PokeListEvent()
    data class ShowToast(val text: String) : PokeListEvent()
}

sealed class PokeListEffect {
    data object NavigateToRegister : PokeListEffect()
    data object NavigateToHome : PokeListEffect()
    data object NavigateToDetail : PokeListEffect()
    data object NavigateToProfile : PokeListEffect()
    data class ShowToast(val text: String) : PokeListEffect()
}

enum class PokeListNav(val route: String) {
    LOGIN("/login"),
    REGISTER("/register"),
    HOME("/home"),
    DETAIL("/detail"),
    PROFILE("/profile")
}

sealed class ToolBarState {
    abstract val name: String
    abstract val showBackButton: Boolean

    data object Login : ToolBarState() {
        override val name: String
            get() = "Login"
        override val showBackButton: Boolean
            get() = false
    }

    data object Register : ToolBarState() {
        override val name: String
            get() = "Register"
        override val showBackButton: Boolean
            get() = true
    }

    data object Home : ToolBarState() {
        override val name: String
            get() = "Home"
        override val showBackButton: Boolean
            get() = false
    }

    data class Detail(override val name: String) : ToolBarState() {
        override val showBackButton: Boolean
            get() = true
    }

    data object Profile : ToolBarState() {
        override val name: String
            get() = "Profile"
        override val showBackButton: Boolean
            get() = true
    }
}