package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.cryptotracker.core.data.networking.SafeCall
import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.ResponseResult
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.mappers.*
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinHistoryResponseDto
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource (
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): ResponseResult<List<Coin>, NetworkError> {
        return SafeCall<CoinsResponseDto> {
             httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): ResponseResult<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return SafeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}