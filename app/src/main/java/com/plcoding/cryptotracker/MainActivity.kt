package com.plcoding.cryptotracker

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import com.plcoding.cryptotracker.core.navigation.*
import com.plcoding.cryptotracker.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  AdaptiveCoinListDetailPane(
                      modifier = Modifier.padding(innerPadding)
                  )
                }
            }
        }
    }
}

