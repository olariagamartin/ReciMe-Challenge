@file:OptIn(ExperimentalContracts::class)

package com.themarto.recimechallenge.core.data.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<T> {

    class Success<T>(val value: T) : Result<T>()
    class Error<T>(val message: String) : Result<T>()
}

fun <T> Result<T>.isSuccess(): Boolean{
    contract {
        returns(true) implies (this@isSuccess is Result.Success)
        returns(false) implies (this@isSuccess is Result.Error)
    }
    return this is Result.Success
}

fun <T> Result<T>.isError(): Boolean {
    contract {
        returns(true) implies (this@isError is Result.Error)
        returns(false) implies (this@isError is Result.Success)
    }
    return this is Result.Error
}