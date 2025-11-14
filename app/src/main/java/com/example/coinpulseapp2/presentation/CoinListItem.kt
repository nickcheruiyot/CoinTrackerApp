package com.example.coinpulseapp2.presentation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coinpulseapp2.domain.model.Coin
import com.example.coinpulseapp2.presentation.components.CoinLogo
import com.example.coinpulseapp2.presentation.components.SparklineGraph

@Composable
fun CoinListItem(
    coin: Coin,
    onClick: () -> Unit
) {
    val changeColor = if (coin.change.startsWith("-")) Color.Red else Color(0xFF4CAF50)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Coin Icon + Name + Symbol
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinLogo(
                iconUrl = coin.iconUrl,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = coin.name,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = coin.symbol,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        // Price
        Text(
            text = "$${coin.price.take(8)}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        // 24h change + sparkline
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${coin.change}%",
                fontSize = 13.sp,
                color = changeColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(25.dp)
            ) {
                SparklineGraph(
                    data = coin.sparkline,
                    color = changeColor
                )
            }
        }
    }

    // Divider
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
    )
}