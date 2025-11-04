package com.example.coinpulseapp2.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.coinpulseapp2.data.remote.repository.CoinRepository
import com.example.coinpulseapp2.presentation.components.CoinDetailViewModel
import com.example.coinpulseapp2.presentation.components.CoinDetailViewModelFactory

@Composable
fun CoinDetailScreen(
    coinId: String,
    repository: CoinRepository
) {
    val viewModel: CoinDetailViewModel = viewModel(
        factory = CoinDetailViewModelFactory(repository)
    )

    val coinDetail by viewModel.coinDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Fetch data only once when the coinId changes
    LaunchedEffect(coinId) {
        viewModel.getCoinDetail(coinId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error != null -> {
                Text(
                    text = error ?: "Unknown error occurred",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            coinDetail != null -> {
                val coin = coinDetail!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //  Show SVG or normal image
                    AsyncImage(
                        model = coin.iconUrl,
                        contentDescription = coin.name,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = coin.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Symbol: ${coin.symbol}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Price: $${coin.price}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Market Cap: $${coin.marketCap}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = coin.description ?: "No description available.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}