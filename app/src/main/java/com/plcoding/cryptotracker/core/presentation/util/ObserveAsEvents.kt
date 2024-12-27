package com.plcoding.cryptotracker.core.presentation.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptotracker.core.domain.util.toString
import com.plcoding.cryptotracker.crypto.presentation.coinlist.CoinListEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


@Composable
fun <T>  ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    // this will only run once
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2 ) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // This is the workaround to ensure that events are not lost
            // which can cause a state mismatch
            withContext(Dispatchers.Main.immediate) {
                    events.collect(onEvent)
            }
        }
    }
}