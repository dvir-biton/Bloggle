package com.fylora.auth.domain.use_case

class ValidateUsernameUseCase {

    operator fun invoke(username: String): ValidationResult<Boolean> {
        if(username.length < 3) {
            return ValidationResult.Error(
                data = false,
                message = "The username cannot be less than 3 characters"
            )
        }

        if(username.length > 12) {
            return ValidationResult.Error(
                data = false,
                message = "The username cannot be more than 12 characters"
            )
        }

        return ValidationResult.Success(true)
    }
}