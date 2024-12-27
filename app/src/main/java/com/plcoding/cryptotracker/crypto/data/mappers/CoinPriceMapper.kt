package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTimestamp = time.toZonedDateTime()
    )
}

private fun Long.toZonedDateTime(zoneId: ZoneId = ZoneId.of("UTC")): ZonedDateTime {
   return Instant.ofEpochMilli(this).atZone(zoneId)
}
