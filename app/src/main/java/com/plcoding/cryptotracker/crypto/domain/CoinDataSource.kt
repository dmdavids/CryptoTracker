package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.ResponseResult

interface CoinDataSource {
    suspend fun getCoins(): ResponseResult<List<Coin>, NetworkError>
}