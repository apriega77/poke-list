package com.apriega77.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apriega77.presentation.PokeListEvent
import com.apriega77.presentation.ToolBarState

@Composable
fun LoginScreen(pokeListEvent: (PokeListEvent) -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        pokeListEvent(PokeListEvent.SetToolBarState { ToolBarState.Login })
        viewModel.sendEvent(LoginEvent.CheckIsUserSignedInUseCase)
        viewModel.effect.collect {
            when (it) {
                LoginEffect.NavigateToHome -> pokeListEvent(PokeListEvent.NavigateToHomeContainer)
                LoginEffect.NavigateToRegister -> pokeListEvent(PokeListEvent.NavigateToRegister)
                is LoginEffect.ShowToast -> pokeListEvent(PokeListEvent.ShowToast(it.text))
            }
        }
    }

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(
            value = state.username,
            label = { Text("Username") },
            onValueChange = {
                viewModel.sendEvent(LoginEvent.Username(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        TextField(
            value = state.password,
            label = { Text("Password") },
            onValueChange = {
                viewModel.sendEvent(LoginEvent.Password(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            onClick = { viewModel.sendEvent(LoginEvent.Login) }) {
            Text("Login")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            onClick = { viewModel.sendEvent(LoginEvent.NavigateToRegister) }) {
            Text("Register")
        }
    }
}