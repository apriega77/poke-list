package com.apriega77.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apriega77.presentation.PokeListEvent
import com.apriega77.presentation.ToolBarState

@Composable
fun DetailScreen(name: String, pokeListEvent: (PokeListEvent) -> Unit) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        pokeListEvent(PokeListEvent.SetToolBarState { ToolBarState.Detail(name) })
        viewModel.sendEvent(DetailEvent.GetData(name))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is DetailState.Loaded -> {
                LazyColumn {
                    items((state as DetailState.Loaded).detail.ability) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = it,
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

            DetailState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }


}