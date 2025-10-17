package com.example.midterm8911139.util

import androidx.annotation.StringRes
import com.example.midterm8911139.R

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(
        val field: Field,
        @StringRes val messageRes: Int
    ) : ValidationResult()
}

enum class Field { EVENT_NAME, ORGANIZER, LOCATION, EVENT_TYPE }

object Validators {
    fun notBlank(value: String, field: Field): ValidationResult =
        if (value.isBlank()) {
            ValidationResult.Error(
                field,
                when (field) {
                    Field.EVENT_NAME -> R.string.error_event_name_required
                    Field.ORGANIZER  -> R.string.error_organizer_required
                    Field.LOCATION   -> R.string.error_location_required
                    else -> R.string.error_event_name_required
                }
            )
        } else ValidationResult.Success
}
