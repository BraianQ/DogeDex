package com.example.dogedex

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dogedex.navigation.AppNavHost
import com.example.dogedex.ui.permission.RequestPermissions
import com.example.dogedex.ui.theme.DogeDexTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogeDexTheme {
                RequestPermissions(
                    permissions = listOf(
                        Manifest.permission.CAMERA
                    )
                )

                val uiState by mainViewModel.uiState.collectAsState()

                when (val state = uiState) {
                    is MainUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is MainUiState.Success -> {
                        AppNavHost(startDestination = state.startDestination)
                    }
                }
            }
        }
    }
}
