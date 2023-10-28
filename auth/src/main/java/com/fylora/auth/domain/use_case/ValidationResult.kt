package com.fylora.auth.domain.use_case

sealed class ValidationResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): ValidationResult<T>(data)
    class Error<T>(message: String, data: T? = null): ValidationResult<T>(data, message)
}
