package com.darkmisfit.omni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.darkmisfit.omni.theme.OmniTheme
import com.darkmisfit.omni.ui.navigation.OmniApp
import com.darkmisfit.omni.viewmodel.OmniViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: OmniViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OmniTheme {
                val uiState by viewModel.uiState.collectAsState()
                OmniApp(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        }
    }
}
