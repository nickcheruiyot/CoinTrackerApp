package com.example.coinpulseapp2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.coinpulseapp2.data.remote.RetrofitInstance
import com.example.coinpulseapp2.data.remote.repository.CoinRepositoryImpl
import com.example.coinpulseapp2.presentation.components.CoinDetailViewModel
import com.example.coinpulseapp2.presentation.components.CoinDetailViewModelFactory
import com.example.coinpulseapp2.presentation.navigation.BottomNavigationBar
import com.example.coinpulseapp2.ui.theme.CoinPulseApp2Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coinpulseapp2.presentation.CoinDetailScreen
import com.example.coinpulseapp2.presentation.CoinListScreen
import com.example.coinpulseapp2.presentation.CoinViewModel
import com.example.coinpulseapp2.presentation.CoinViewModelFactory
import com.example.coinpulseapp2.presentation.FavoritesScreen

class MainActivity : ComponentActivity() {

    // Repository for fetching data
    private val repository by lazy { CoinRepositoryImpl(RetrofitInstance.api) }

    // Coin list ViewModel
    private val coinViewModel: CoinViewModel by viewModels {
        CoinViewModelFactory(repository)
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPulseApp2Theme {
                var selectedCoinId by remember { mutableStateOf<String?>(null) }
                var selectedTab by remember { mutableStateOf("home") }

                Scaffold(
                    bottomBar = {
                        if (selectedCoinId == null) {
                            BottomNavigationBar(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    }
                ) { innerPadding ->

                    AnimatedContent(
                        targetState = selectedCoinId,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) togetherWith
                                    fadeOut(animationSpec = tween(300))
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) { coinId ->
                        if (coinId == null) {
                            // Show Coin List or Favorites
                            when (selectedTab) {
                                "home" -> CoinListScreen(
                                    viewModel = coinViewModel,
                                    onCoinClick = { selectedCoinId = it }
                                )
                                "favorites" -> FavoritesScreen()
                            }
                        } else {
                            // Coin Detail Screen with its own ViewModel
                            val coinDetailViewModel: CoinDetailViewModel = viewModel(
                                factory = CoinDetailViewModelFactory(repository)
                            )

                            CoinDetailScreen(
                                coinId = coinId,
                                viewModel = coinDetailViewModel,
                                onBackClick = { selectedCoinId = null }
                            )
                        }
                    }
                }
            }
        }
    }
}
