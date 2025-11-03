package com.example.coinpulseapp2.data.remote

data class CoinDetailDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val description: String?,
    val color: String?,
    val iconUrl: String?,
    val websiteUrl: String?,
    val numberOfMarkets: Int?,
    val numberOfExchanges: Int?,
    val marketCap: String?,
    val price: String?,
    val change: String?,
    val rank: Int?,
    val `24hVolume`: String?,
    val allTimeHigh: AllTimeHigh?,
    val supply: Supply?
)

data class AllTimeHigh(
    val price: String?,
    val timestamp: Long?
)

