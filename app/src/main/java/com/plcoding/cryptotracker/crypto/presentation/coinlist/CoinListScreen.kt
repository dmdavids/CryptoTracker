package com.plcoding.cryptotracker.crypto.presentation.coinlist


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.domain.util.toString
import com.plcoding.cryptotracker.crypto.presentation.coinlist.components.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coinlist.components.previewCoin
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext

@Composable
fun CoinListScreen(
    state: CoinListState,
    events: Flow<CoinListEvent>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    // this will only run once
    LaunchedEffect(true) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // This is the workaround to ensure that events are not lost
            // which can cause a state mismatch
            withContext(Dispatchers.Main.immediate) {
                events.collect { event ->
                    when (event) {
                        is CoinListEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = {},
                    modifier = Modifier. fillMaxSize()
                )
                HorizontalDivider()
            }
        }
    }

}

@PreviewLightDark
@Composable
private  fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..10).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            events = emptyFlow(),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}