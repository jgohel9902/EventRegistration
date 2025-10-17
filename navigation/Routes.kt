package com.example.midterm8911139.ui.navigation

sealed class Route(val path: String) {
    data object Registration : Route("registration")
    data object Summary : Route("summary")
}
