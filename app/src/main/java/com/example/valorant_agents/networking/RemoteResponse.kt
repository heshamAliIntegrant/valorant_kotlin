package com.example.valorant_agents.networking

/**
 * @param T type of body when the response is [Success].
 * @param E type of error when the response is [Error].
 */
sealed class RemoteResponse<T, E> {
    data class Success<T, E>(val body: T) : RemoteResponse<T, E>()
    data class Error<T, E>(val error: E) : RemoteResponse<T, E>()
    companion object {
        fun <T, E> createError(error: E): RemoteResponse<T, E> {
            return Error(error)
        }
        fun <T, E> createSuccess(data: T): RemoteResponse<T, E> {
            return Success(data)
        }
    }
}