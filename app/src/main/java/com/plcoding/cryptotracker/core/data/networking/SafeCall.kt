package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.ResponseResult
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> SafeCall(
    execute: () -> HttpResponse
) : ResponseResult<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return ResponseResult.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return ResponseResult.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ResponseResult.Error(NetworkError.UNKNOWN)
    }
    return reponseToResult(response)
}