package com.example.midterm8911139

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.midterm8911139.ui.navigation.Route
import com.example.midterm8911139.ui.screens.EventRegistrationScreen
import com.example.midterm8911139.ui.screens.OrderSummaryScreen
import com.example.midterm8911139.ui.theme.MidTermTheme
import com.example.midterm8911139.ui.viewmodel.EventViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidTermApp()
        }
    }
}

@Composable
fun MidTermApp() {
    MidTermTheme {
        val nav = rememberNavController()
        val vm: EventViewModel = viewModel()

        Scaffold { padding ->
            NavHost(
                navController = nav,
                startDestination = Route.Registration.path
            ) {
                composable(Route.Registration.path) {
                    EventRegistrationScreen(
                        paddingValues = padding,
                        viewModel = vm,
                        onSuccess = {
                            Toast.makeText(
                                it, it.getString(R.string.toast_success), Toast.LENGTH_SHORT
                            ).show()
                            nav.navigate(Route.Summary.path)
                        }
                    )
                }
                composable(Route.Summary.path) {
                    OrderSummaryScreen(
                        paddingValues = padding,
                        viewModel = vm,
                        onBack = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}
