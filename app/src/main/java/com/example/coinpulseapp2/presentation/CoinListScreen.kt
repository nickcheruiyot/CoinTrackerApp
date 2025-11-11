package com.example.coinpulseapp2.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinpulseapp2.domain.model.Coin
import com.example.coinpulseapp2.presentation.components.SparklineGraph

@Composable
fun CoinListScreen(
    viewModel: CoinViewModel,
    onCoinClick: (String) -> Unit
) {
    // Fetch coins when screen opens
    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    val coins by viewModel.coins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 12.dp)
    ) {
        // Title
        Text(
            text = "Top 100 Coins",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Coin",
                color = Color.Gray,
                fontSize = 13.sp,
                modifier = Modifier.weight(1.8f)
            )
            Text(
                text = "Price",
                color = Color.Gray,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.2f)
            )
            Text(
                text = "24h",
                color = Color.Gray,
                fontSize = 13.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1.8f)
            )
        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", color = Color.Gray)
                }
            }

            coins.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No coins found", color = Color.Gray)
                }
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(coins) { coin ->
                        CoinListItem(
                            coin = coin,
                            onClick = { onCoinClick(coin.uuid) }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CoinListItem(
    coin: Coin,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    // Enable SVG support
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val changeColor = if (coin.change.startsWith("-")) Color.Red else Color(0xFF4CAF50)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Icon + Name + Symbol
        Row(
            modifier = Modifier.weight(1.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(coin.iconUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader
                ),
                contentDescription = coin.name,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = coin.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = coin.symbol,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Middle: Price
        Text(
            text = "$${coin.price.take(8)}",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.2f)
        )

        // Right: 24h change + sparkline
        Column(
            modifier = Modifier
                .weight(1.8f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${coin.change}%",
                color = changeColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(24.dp)
            ) {
                SparklineGraph(
                    data = coin.sparkline,
                    color = changeColor
                )
            }
        }
    }
}