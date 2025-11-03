package com.example.coinpulseapp2.data.remote.mapper

import com.example.coinpulseapp2.domain.model.Stats

import com.example.coinpulseapp2.data.remote.Stats as StatsDto
import com.example.coinpulseapp2.domain.model.Stats as DomainStats

object StatsMapper {
    fun mapToDomain(dto: StatsDto): DomainStats {
        return DomainStats(
            total = dto.total,
            total24hVolume = dto.total24hVolume,
            totalCoins = dto.totalCoins,
            totalExchanges = dto.totalExchanges,
            totalMarketCap = dto.totalMarketCap,
            totalMarkets = dto.totalMarkets
        )
    }
}