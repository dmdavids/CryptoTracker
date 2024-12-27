package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.ResponseResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> reponseToResult(
    response: HttpResponse
): ResponseResult<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                ResponseResult.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                ResponseResult.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> ResponseResult.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> ResponseResult.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> ResponseResult.Error(NetworkError.SERVER_ERROR)
        else -> ResponseResult.Error(NetworkError.UNKNOWN)
    }
}
