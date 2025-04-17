package com.apriega77.presentation.register

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
import com.apriega77.presentation.login.LoginEvent
import kotlinx.coroutines.flow.collect

@Composable
fun RegisterScreen(pokeListEvent: (PokeListEvent) -> Unit) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        pokeListEvent(PokeListEvent.SetToolBarState { ToolBarState.Register })
        viewModel.effect.collect {
            when (it) {
                RegisterEffect.NavigateToHome -> {
                    pokeListEvent(PokeListEvent.NavigateToHomeContainer)
                }

                is RegisterEffect.ShowToast -> {
                    pokeListEvent(PokeListEvent.ShowToast(it.text))
                }
            }
        }
    }

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(
            value = state.name,
            label = { Text("Name") },
            onValueChange = {
                viewModel.sendEvent(RegisterEvent.Name(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        TextField(
            value = state.username,
            label = { Text("Username") },
            onValueChange = {
                viewModel.sendEvent(RegisterEvent.Username(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        TextField(
            value = state.password,
            label = { Text("Password") },
            onValueChange = {
                viewModel.sendEvent(RegisterEvent.Password(it))
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
            enabled = state.enableButton,
            onClick = { viewModel.sendEvent(RegisterEvent.Register) }) {
            Text("Register")
        }
    }
}