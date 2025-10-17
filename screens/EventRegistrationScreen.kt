package com.example.midterm8911139.ui.screens

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.midterm8911139.R
import com.example.midterm8911139.model.AdditionalService
import com.example.midterm8911139.model.EventType
import com.example.midterm8911139.ui.viewmodel.EventViewModel
import com.example.midterm8911139.util.ValidationResult

@Composable
fun EventRegistrationScreen(
    paddingValues: PaddingValues,
    viewModel: EventViewModel,
    onSuccess: (Context) -> Unit
) {
    val state = viewModel.state
    val lastValidation = viewModel.lastValidation
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .imePadding(), // lifts button above keyboard
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = stringResource(R.string.title_event_registration),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(6.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.eventName,
            onValueChange = viewModel::updateEventName,
            label = { Text(stringResource(R.string.hint_event_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = state.organizerName,
            onValueChange = viewModel::updateOrganizerName,
            label = { Text(stringResource(R.string.hint_organizer_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = state.eventLocation,
            onValueChange = viewModel::updateEventLocation,
            label = { Text(stringResource(R.string.hint_event_location)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.event_type),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f)
            )
            RadioWithLabel(
                selected = state.eventType == EventType.CONFERENCE,
                onClick = { viewModel.updateEventType(EventType.CONFERENCE) },
                text = stringResource(R.string.type_conference)
            )
            RadioWithLabel(
                selected = state.eventType == EventType.WEDDING,
                onClick = { viewModel.updateEventType(EventType.WEDDING) },
                text = stringResource(R.string.type_wedding)
            )
            RadioWithLabel(
                selected = state.eventType == EventType.CONCERT,
                onClick = { viewModel.updateEventType(EventType.CONCERT) },
                text = stringResource(R.string.type_concert)
            )
        }

        Spacer(Modifier.height(6.dp))

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.additional_services),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                ServiceCheckbox(
                    labelRes = R.string.service_catering,
                    checked = state.services.contains(AdditionalService.CATERING),
                    onCheckedChange = { viewModel.toggleService(AdditionalService.CATERING) }
                )
                Spacer(Modifier.width(8.dp))
                ServiceCheckbox(
                    labelRes = R.string.service_photography,
                    checked = state.services.contains(AdditionalService.PHOTOGRAPHY),
                    onCheckedChange = { viewModel.toggleService(AdditionalService.PHOTOGRAPHY) }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                ServiceCheckbox(
                    labelRes = R.string.service_music,
                    checked = state.services.contains(AdditionalService.MUSIC),
                    onCheckedChange = { viewModel.toggleService(AdditionalService.MUSIC) }
                )
                Spacer(Modifier.width(8.dp))
                ServiceCheckbox(
                    labelRes = R.string.service_decoration,
                    checked = state.services.contains(AdditionalService.DECORATION),
                    onCheckedChange = { viewModel.toggleService(AdditionalService.DECORATION) }
                )
            }
        }

        // Inline validation message if needed
        if (lastValidation is ValidationResult.Error) {
            Spacer(Modifier.height(6.dp))
            AssistiveErrorText(messageRes = lastValidation.messageRes)
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (viewModel.validateAndRegister()) {
                    onSuccess(context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
        ) {
            Text(text = stringResource(R.string.btn_register))
        }
    }
}

@Composable
private fun RadioWithLabel(
    selected: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 6.dp)
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ServiceCheckbox(
    @StringRes labelRes: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = stringResource(labelRes), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun AssistiveErrorText(@StringRes messageRes: Int) {
    Text(
        text = stringResource(messageRes),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall
    )
}

