package com.apriega77.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apriega77.presentation.PokeListEvent

@Composable
fun HomeScreen(pokeListEvent: (PokeListEvent) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(Unit) {
        viewModel.sendEvent(HomeEvent.GetPokemon)
        viewModel.effect.collect {
            when (it) {
                is HomeEffect.NavigateToDetail -> {
                    pokeListEvent(PokeListEvent.NavigateToDetail(it.name))
                }
            }
        }
    }

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            reachedBottom(lazyListState)
        }
    }
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) viewModel.sendEvent(HomeEvent.GetPokemon)
    }

    Box(Modifier.fillMaxSize()) {
        when (state) {
            HomeState.Failed -> {
                Box(Modifier.fillMaxSize()) {
                    Button(
                        modifier = Modifier.align(Alignment.Center), onClick =
                            { viewModel.sendEvent(HomeEvent.GetPokemon) }) {
                        Text(text = "Reload")
                    }
                }
            }

            HomeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is HomeState.Success -> {

                LazyColumn(state = lazyListState) {

                    items((state as HomeState.Success).data) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    pokeListEvent(PokeListEvent.NavigateToDetail(it.name))
                                }) {
                            Text(
                                text = it.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.CenterStart),
                                fontSize = TextUnit(16F, TextUnitType.Sp)
                            )
                        }
                    }

                }

            }
        }
    }


}

fun reachedBottom(lazyListState: LazyListState): Boolean {
    val layoutInfo = lazyListState.layoutInfo
    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return false
    val totalItemsCount = layoutInfo.totalItemsCount

    val isListScrollable = layoutInfo.visibleItemsInfo.size < totalItemsCount
    val isLastItemVisible = lastVisibleItemIndex >= totalItemsCount - 1


    return !isListScrollable || isLastItemVisible}