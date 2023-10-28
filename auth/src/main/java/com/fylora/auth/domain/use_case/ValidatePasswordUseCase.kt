package com.fylora.auth.domain.use_case

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult<Boolean> {
        if(!isStrongPassword(password)) {
            return ValidationResult.Error(
                data = false,
                message = "The password is not strong enough"
            )
        }

        return ValidationResult.Success(true)
    }

    private fun isStrongPassword(password: String): Boolean {
        val hasUpperCase = password.any {
            it.isUpperCase()
        }
        val hasLowerCase = password.any {
            it.isLowerCase()
        }
        val hasDigit = password.any {
            it.isDigit()
        }
        val hasSpecialChar = password.any {
            it.isLetterOrDigit().not()
        }

        return password.length >= MIN_PASSWORD_LENGTH
                && hasUpperCase
                && hasLowerCase
                && hasDigit
                && hasSpecialChar
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}