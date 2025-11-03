package com.example.coinpulseapp2.data.remote.mapper

import com.example.coinpulseapp2.data.remote.CoinDetailDto
import com.example.coinpulseapp2.domain.model.CoinDetail

object CoinDetailMapper {
    fun mapToDomain(dto: CoinDetailDto): CoinDetail {
        return CoinDetail(
            id = dto.uuid,
            name = dto.name,
            symbol = dto.symbol,
            description = dto.description ?: "No description available",
            iconUrl = dto.iconUrl ?: "",
            color = dto.color ?: "#000000",
            price = dto.price ?: "0",
            marketCap = dto.marketCap ?: "0",
            change = dto.change ?: "0",
            rank = dto.rank ?: 0,
            websiteUrl = dto.websiteUrl ?: "",
            numberOfMarkets = dto.numberOfMarkets ?: 0,
            numberOfExchanges = dto.numberOfExchanges ?: 0,
            supply = dto.supply?.circulating,
            allTimeHigh = dto.allTimeHigh?.price
        )
    }
}