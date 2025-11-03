package com.example.coinpulseapp2.data.remote.repository

import com.example.coinpulseapp2.domain.model.Coin
import com.example.coinpulseapp2.domain.model.CoinDetail

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
}