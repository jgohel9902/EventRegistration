package com.example.midterm8911139.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.midterm8911139.R
import com.example.midterm8911139.model.AdditionalService
import com.example.midterm8911139.model.EventType
import com.example.midterm8911139.ui.viewmodel.EventViewModel

@Composable
fun OrderSummaryScreen(
    paddingValues: PaddingValues,
    viewModel: EventViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.state

    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_event_summary),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        SummaryRow(label = stringResource(R.string.hint_event_name), value = state.eventName)
        SummaryRow(label = stringResource(R.string.hint_organizer_name), value = state.organizerName)
        SummaryRow(label = stringResource(R.string.hint_event_location), value = state.eventLocation)

        val typeString = when (state.eventType) {
            EventType.CONFERENCE -> stringResource(R.string.type_conference)
            EventType.WEDDING    -> stringResource(R.string.type_wedding)
            EventType.CONCERT    -> stringResource(R.string.type_concert)
            null                 -> "-"
        }
        SummaryRow(label = stringResource(R.string.event_type), value = typeString)

        val services = buildList {
            if (state.services.contains(AdditionalService.CATERING)) add(stringResource(R.string.service_catering))
            if (state.services.contains(AdditionalService.PHOTOGRAPHY)) add(stringResource(R.string.service_photography))
            if (state.services.contains(AdditionalService.MUSIC)) add(stringResource(R.string.service_music))
            if (state.services.contains(AdditionalService.DECORATION)) add(stringResource(R.string.service_decoration))
        }.ifEmpty { listOf("-") }.joinToString(", ")

        SummaryRow(label = stringResource(R.string.additional_services), value = services)

        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) {
                Text(text = stringResource(R.string.btn_back))
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
