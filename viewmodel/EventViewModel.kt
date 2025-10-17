package com.example.midterm8911139.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.midterm8911139.model.AdditionalService
import com.example.midterm8911139.model.EventType
import com.example.midterm8911139.util.Field
import com.example.midterm8911139.util.ValidationResult
import com.example.midterm8911139.util.Validators

data class EventFormState(
    val eventName: String = "",
    val organizerName: String = "",
    val eventLocation: String = "",
    val eventType: EventType? = null,
    val services: Set<AdditionalService> = emptySet()
)

class EventViewModel : ViewModel() {

    var state by mutableStateOf(EventFormState())
        private set

    var lastValidation: ValidationResult? by mutableStateOf(null)
        private set

    fun updateEventName(v: String) { state = state.copy(eventName = v) }
    fun updateOrganizerName(v: String) { state = state.copy(organizerName = v) }
    fun updateEventLocation(v: String) { state = state.copy(eventLocation = v) }
    fun updateEventType(t: EventType) { state = state.copy(eventType = t) }

    fun toggleService(service: AdditionalService) {
        val s = state.services.toMutableSet()
        if (s.contains(service)) s.remove(service) else s.add(service)
        state = state.copy(services = s)
    }

    fun validateAndRegister(): Boolean {
        // Validate field-by-field (first error short-circuits)
        when (val vr = Validators.notBlank(state.eventName, Field.EVENT_NAME)) {
            is ValidationResult.Error -> { lastValidation = vr; return false }
            else -> {}
        }
        when (val vr = Validators.notBlank(state.organizerName, Field.ORGANIZER)) {
            is ValidationResult.Error -> { lastValidation = vr; return false }
            else -> {}
        }
        when (val vr = Validators.notBlank(state.eventLocation, Field.LOCATION)) {
            is ValidationResult.Error -> { lastValidation = vr; return false }
            else -> {}
        }
        if (state.eventType == null) {
            lastValidation = ValidationResult.Error(Field.EVENT_TYPE, com.example.midterm8911139.R.string.error_event_type_required)
            return false
        }

        lastValidation = ValidationResult.Success
        return true
    }

    fun reset() {
        state = EventFormState()
        lastValidation = null
    }
}
