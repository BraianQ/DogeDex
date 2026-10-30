package com.example.dogedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dogedex.navigation.AppNavHost
import com.example.dogedex.ui.theme.DogeDexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogeDexTheme {
                AppNavHost()
            }
        }
    }
}
