package com.example.coinpulseapp2.data.remote.repository
import com.example.coinpulseapp2.data.mapper.CoinMapper
import com.example.coinpulseapp2.data.remote.CoinApiService
import com.example.coinpulseapp2.domain.model.Coin
import com.example.coinpulseapp2.domain.model.CoinDetail
class CoinRepositoryImpl(
    private val api: CoinApiService
) : CoinRepository {

    override suspend fun getCoins(): List<Coin> {
        val response = api.getCoins()
        return CoinMapper.mapListToDomain(response.data.coins)
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        val dto = api.getCoinDetail(coinId).data.coin
        return CoinDetail(
            id = dto.uuid,
            name = dto.name,
            symbol = dto.symbol,
            description = dto.description ?: "",
            iconUrl = dto.iconUrl ?: "",
            color = dto.color ?: "",
            price = dto.price ?: "",
            marketCap = dto.marketCap ?: "",
            change = dto.change ?: "",
            rank = dto.rank ?: 0,
            websiteUrl = dto.websiteUrl ?: "",
            numberOfMarkets = dto.numberOfMarkets ?: 0,
            numberOfExchanges = dto.numberOfExchanges ?: 0,
            supply = dto.supply?.total,
            allTimeHigh = dto.allTimeHigh?.price
        )
    }
}

