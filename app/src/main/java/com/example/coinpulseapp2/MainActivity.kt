package com.example.coinpulseapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.coinpulseapp2.presentation.CoinListScreen
import com.example.coinpulseapp2.presentation.CoinViewModel
import com.example.coinpulseapp2.ui.theme.CoinPulseApp2Theme


class MainActivity : ComponentActivity() {

    private val viewModel: CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinPulseApp2Theme {
                CoinListScreen(viewModel = viewModel)
            }
        }
    }
}