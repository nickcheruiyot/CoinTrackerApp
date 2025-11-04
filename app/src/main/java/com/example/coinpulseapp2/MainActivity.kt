package com.example.coinpulseapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinpulseapp2.data.remote.RetrofitInstance
import com.example.coinpulseapp2.data.remote.repository.CoinRepositoryImpl
import com.example.coinpulseapp2.presentation.CoinDetailScreen
import com.example.coinpulseapp2.presentation.CoinListScreen
import com.example.coinpulseapp2.presentation.CoinViewModel
import com.example.coinpulseapp2.ui.theme.CoinPulseApp2Theme

class MainActivity : ComponentActivity() {

    private val viewModel: CoinViewModel by viewModels()
    private val repository by lazy { CoinRepositoryImpl(RetrofitInstance.api) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPulseApp2Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "coin_list",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("coin_list") {
                        CoinListScreen(
                            viewModel = viewModel,
                            onCoinClick = { coinId ->
                                navController.navigate("coin_detail/$coinId")
                            }
                        )
                    }

                    composable("coin_detail/{coinId}") { backStackEntry ->
                        val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
                        CoinDetailScreen(
                            coinId = coinId,
                            repository = repository
                        )
                    }
                }
            }
        }
    }
}