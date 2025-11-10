package com.example.coinpulseapp2.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

    // Fetch coins once when the screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Top Coins",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Name", color = Color.Gray, modifier = Modifier.weight(1.5f))
            Text(text = "Price", color = Color.Gray, modifier = Modifier.weight(1f))
            Text(text = "24h", color = Color.Gray, modifier = Modifier.weight(0.7f), textAlign = androidx.compose.ui.text.style.TextAlign.End)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(coins) { coin ->
                CoinListItem(coin = coin, onItemClick = { onCoinClick(coin.uuid) })
            }
        }

        // loading indicator
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...", color = Color.White)
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
            .padding(vertical = 10.dp)
            .background(Color.Black)
            .clickable { onItemClick(coin.uuid) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coin.iconUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()
        )

        //  Coin Icon
        Image(
            painter = painter,
            contentDescription = coin.name,
            modifier = Modifier
                .size(32.dp)
                .padding(end = 12.dp)
        )

        //  Coin Name
        Text(
            text = coin.name,
            color = Color.White,
            modifier = Modifier.weight(1.5f),
            style = MaterialTheme.typography.bodyLarge
        )

        //  Price
        Text(
            text = "$${coin.price.take(8)}",
            color = Color.White,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        //  24h Change (Red if negative, Green if positive)
        val isNegative = coin.change.startsWith("-")
        Text(
            text = "${coin.change}%",
            color = if (isNegative) Color.Red else Color(0xFF00C853),
            modifier = Modifier.weight(0.7f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}