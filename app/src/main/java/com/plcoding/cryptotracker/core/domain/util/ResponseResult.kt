package com.plcoding.cryptotracker.core.domain.util

typealias DomainError = Error

sealed interface ResponseResult<out D, out E: Error> {
    data class Success<out D>(val data: D): ResponseResult<D, Nothing>
    data class Error<out E: DomainError>(val error: E): ResponseResult<Nothing, E>
}

inline fun <T, E: Error, R> ResponseResult<T, E>.map(map: (T) -> R): ResponseResult<R, E> {
    return when(this) {
        is ResponseResult.Error -> ResponseResult.Error(error)
        is ResponseResult.Success -> ResponseResult.Success(map(data))
    }
}

fun <T, E: Error> ResponseResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> ResponseResult<T, E>.onSuccess(action: (T) -> Unit): ResponseResult<T, E> {
    return when(this) {
        is ResponseResult.Error -> this
        is ResponseResult.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> ResponseResult<T, E>.onError(action: (E) -> Unit): ResponseResult<T, E> {
    return when(this) {
        is ResponseResult.Error -> {
            action(error)
            this
        }
        is ResponseResult.Success -> this
    }
}

typealias EmptyResult<E> = ResponseResult<Unit, E>