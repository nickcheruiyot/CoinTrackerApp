package com.example.coinpulseapp2.domain.model

data class CoinDetail(
    val id: String,
    val name: String,
    val symbol: String,
    val description: String,
    val iconUrl: String,
    val color: String,
    val price: String,
    val marketCap: String,
    val change: String,
    val rank: Int,
    val websiteUrl: String,
    val numberOfMarkets: Int,
    val numberOfExchanges: Int,
    val supply: String?,
    val allTimeHigh: String?
)
