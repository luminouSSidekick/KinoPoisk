package com.example.films.data

data class Resource<out T>(val status: NetworkStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(NetworkStatus.SUCCESS, data, null)
        }
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(NetworkStatus.ERROR, data, msg)
        }
    }
}

enum class NetworkStatus {
    SUCCESS,
    ERROR
}

