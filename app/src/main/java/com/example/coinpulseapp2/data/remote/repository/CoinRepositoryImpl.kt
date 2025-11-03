package com.example.coinpulseapp2.data.remote.repository

import com.example.coinpulseapp2.data.mapper.CoinMapper
import com.example.coinpulseapp2.data.remote.CoinApiService
import com.example.coinpulseapp2.data.remote.mapper.CoinDetailMapper
import com.example.coinpulseapp2.domain.model.Coin
import com.example.coinpulseapp2.domain.model.CoinDetail

class CoinRepositoryImpl(
    private val apiService: CoinApiService
) : CoinRepository {

    override suspend fun getCoins(): List<Coin> {
        val response = apiService.getCoins()
        return CoinMapper.mapListToDomain(response.data.coins)
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        val response = apiService.getCoinDetail(coinId)
        return CoinDetailMapper.mapToDomain(response.data.coin)
    }
}
