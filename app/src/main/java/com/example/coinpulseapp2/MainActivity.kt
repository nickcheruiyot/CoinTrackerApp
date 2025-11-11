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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.coinpulseapp2.data.remote.RetrofitInstance
import com.example.coinpulseapp2.data.remote.repository.CoinRepositoryImpl
import com.example.coinpulseapp2.presentation.CoinDetailScreen
import com.example.coinpulseapp2.presentation.CoinListScreen
import com.example.coinpulseapp2.presentation.CoinViewModel
import com.example.coinpulseapp2.ui.theme.CoinPulseApp2Theme

class MainActivity : ComponentActivity() {

    private val viewModel: CoinViewModel by viewModels()
    private val repository by lazy { CoinRepositoryImpl(RetrofitInstance.api) }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPulseApp2Theme {
                var selectedCoinId by remember { mutableStateOf<String?>(null) }

                AnimatedContent(
                    targetState = selectedCoinId,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    modifier = Modifier.fillMaxSize()
                ) { coinId ->
                    if (coinId == null) {
                        //  Show Coin List Screen
                        CoinListScreen(
                            viewModel = viewModel,
                            onCoinClick = { selectedCoinId = it }
                        )
                    } else {
                        // Show Coin Detail Screen
                        CoinDetailScreen(
                            coinId = coinId,
                            repository = repository,
                            onBackClick = { selectedCoinId = null }
                        )
                    }
                }
            }
        }
    }
}

