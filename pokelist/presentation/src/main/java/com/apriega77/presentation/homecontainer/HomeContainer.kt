package com.apriega77.presentation.homecontainer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.apriega77.presentation.PokeListEvent
import com.apriega77.presentation.ToolBarState
import com.apriega77.presentation.home.HomeScreen
import com.apriega77.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun HomeContainer(pokeListEvent: (PokeListEvent) -> Unit) {
    val tabs = listOf("Home", "Profile")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pokeListEvent(PokeListEvent.SetToolBarState { ToolBarState.HomeContainer })
    }
    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }

        HorizontalPager(pageSize = PageSize.Fill, state = pagerState) { page ->
            when (page) {
                0 -> HomeScreen(pokeListEvent)
                1 -> ProfileScreen()
            }
        }
    }
}