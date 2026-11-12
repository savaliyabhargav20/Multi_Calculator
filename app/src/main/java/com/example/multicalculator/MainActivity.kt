package com.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.multicalculator.ui.main.MainScreen
import com.example.multicalculator.ui.theme.MultiCalculatorTheme
import com.example.multicalculator.ui.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiCalculatorTheme {
                var showWelcome by remember { mutableStateOf(true) }
                if (showWelcome) {
                    WelcomeScreen(onGetStarted = { showWelcome = false })
                } else {
                    MainScreen()
                }
            }
        }
    }
}
