package com.apriega77.presentation

data class PokeListState(val name: String, val toolBarState: () -> ToolBarState)

sealed class PokeListEvent {
    data object NavigateToRegister : PokeListEvent()
    data object NavigateToHomeContainer : PokeListEvent()
    data class NavigateToDetail(val name: String) : PokeListEvent()
    data class SetToolBarState(val toolBarState: () -> ToolBarState) : PokeListEvent()
    data class ShowToast(val text: String) : PokeListEvent()
    data object NavigateToLogin : PokeListEvent()
}

sealed class PokeListEffect {
    data object NavigateToRegister : PokeListEffect()
    data object NavigateToHomeContainer : PokeListEffect()
    data object NavigateToDetail : PokeListEffect()
    data class ShowToast(val text: String) : PokeListEffect()
    data object NavigateToLogin : PokeListEffect()
}

enum class PokeListNav(val route: String) {
    LOGIN("/login"),
    REGISTER("/register"),
    HOME_CONTAINER("/home-container"),
    DETAIL("/detail"),
}

sealed class ToolBarState {
    abstract val name: String
    abstract val showBackButton: Boolean
    abstract val showToolBar: Boolean

    data object Login : ToolBarState() {
        override val name: String
            get() = "Login"
        override val showBackButton: Boolean
            get() = false
        override val showToolBar: Boolean
            get() = true
    }

    data object Register : ToolBarState() {
        override val name: String
            get() = "Register"
        override val showBackButton: Boolean
            get() = true
        override val showToolBar: Boolean
            get() = true
    }

    data class Detail(override val name: String) : ToolBarState() {
        override val showBackButton: Boolean
            get() = true
        override val showToolBar: Boolean
            get() = true
    }

    data object HomeContainer : ToolBarState() {
        override val name: String
            get() = ""
        override val showBackButton: Boolean
            get() = false
        override val showToolBar: Boolean
            get() = false
    }

}