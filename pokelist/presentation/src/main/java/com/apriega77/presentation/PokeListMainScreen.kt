package com.apriega77.presentation

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apriega77.presentation.detail.DetailScreen
import com.apriega77.presentation.homecontainer.HomeContainer
import com.apriega77.presentation.login.LoginScreen
import com.apriega77.presentation.register.RegisterScreen

@Composable
fun PokeListMainScreen() {
    val viewModel = hiltViewModel<PokeListViewModel>()
    val state by viewModel.state.collectAsState()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val navController = rememberNavController()
    val context = LocalActivity.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                PokeListEffect.NavigateToDetail -> {
                    navController.navigate(PokeListNav.DETAIL.route)
                }

                PokeListEffect.NavigateToHomeContainer -> {
                    navController.navigate(PokeListNav.HOME_CONTAINER.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }


                PokeListEffect.NavigateToRegister -> {
                    navController.navigate(PokeListNav.REGISTER.route)
                }

                is PokeListEffect.ShowToast -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    val showBackButton by remember(state) { mutableStateOf(state.toolBarState.invoke().showBackButton) }
    val showToolBar by remember(state) { mutableStateOf(state.toolBarState.invoke().showToolBar) }


    Column(Modifier.fillMaxSize()) {
        if (showToolBar) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (showBackButton) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp),
                        onClick = { backPressedDispatcher?.onBackPressed() },
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_back_button),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center)

                        )
                    }
                }

                Text(
                    text = state.toolBarState.invoke().name,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(22F, TextUnitType.Sp)
                )
            }
        }

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            navController = navController,
            startDestination = PokeListNav.LOGIN.route
        ) {
            composable(route = PokeListNav.LOGIN.route) {
                LoginScreen {
                    viewModel.sendEvent(it)
                }
            }

            composable(route = PokeListNav.REGISTER.route) {
                RegisterScreen {
                    viewModel.sendEvent(it)
                }
            }

            composable(route = PokeListNav.HOME_CONTAINER.route) {
                HomeContainer {
                    viewModel.sendEvent(it)
                }
            }


            composable(route = PokeListNav.DETAIL.route) {
                DetailScreen(state.name) {
                    viewModel.sendEvent(it)
                }
            }
        }

    }

}
