package com.example.coinpulseapp2.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinpulseapp2.domain.model.Coin

@Composable
fun CoinListScreen(
    viewModel: CoinViewModel,
    onCoinClick: (String) -> Unit
) {
    val coins = viewModel.coins.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    // Fetch coins once when screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            coins.isEmpty() -> {
                Text(
                    text = "No coins available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(coins) { coin ->
                        CoinListItem(
                            coin = coin,
                            onItemClick = { onCoinClick(coin.uuid) } // 👈 navigate to detail
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(coin.uuid) }
    ) {
        // Load SVG + PNG using Coil
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coin.iconUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = coin.name,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = coin.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Price: $${coin.price}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Change (24h): ${coin.change}%",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}