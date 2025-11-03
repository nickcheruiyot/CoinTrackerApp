// data/mapper/CoinMapper.kt
package com.example.coinpulseapp2.data.mapper

import com.example.coinpulseapp2.data.remote.CoinDto
import com.example.coinpulseapp2.domain.model.Coin as DomainCoin

object CoinMapper {

    fun mapToDomain(dto: CoinDto): DomainCoin {
        return DomainCoin(
            `24hVolume` = dto.`24hVolume` ?: "",
            btcPrice = dto.btcPrice ?: "",
            change = dto.change ?: "",
            coinrankingUrl = dto.coinrankingUrl ?: "",
            color = dto.color ?: "",
            contractAddresses = dto.contractAddresses ?: emptyList(),
            iconUrl = dto.iconUrl ?: "",
            isWrappedTrustless = dto.isWrappedTrustless ?: false,
            listedAt = dto.listedAt ?: 0,
            lowVolume = dto.lowVolume ?: false,
            marketCap = dto.marketCap ?: "",
            name = dto.name ?: "",
            price = dto.price ?: "",
            rank = dto.rank ?: 0,
            sparkline = dto.sparkline ?: emptyList(),
            symbol = dto.symbol ?: "",
            tier = dto.tier ?: 0,
            uuid = dto.uuid ?: "",
            wrappedTo = dto.wrappedTo ?: ""
        )
    }

    fun mapListToDomain(dtos: List<CoinDto>): List<DomainCoin> {
        return dtos.map { mapToDomain(it) }
    }
}