package com.cradlesoft.medreminder.core.domain

sealed class Result<out T> {
    object Loading: Result<Nothing>()
    data class Success<out T>(val result: T): Result<T>()
    data class Error(val message: String): Result<Nothing>()
}