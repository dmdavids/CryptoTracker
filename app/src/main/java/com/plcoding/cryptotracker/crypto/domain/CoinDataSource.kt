package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.ResponseResult
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): ResponseResult<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
        ): ResponseResult<List<CoinPrice>, NetworkError>
}