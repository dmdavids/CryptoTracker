package com.plcoding.cryptotracker.core.navigation

import androidx.compose.material3.adaptive.*
import androidx.compose.material3.adaptive.layout.*
import androidx.compose.material3.adaptive.navigation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.compose.*
import com.plcoding.cryptotracker.core.domain.util.*
import com.plcoding.cryptotracker.core.presentation.util.*
import com.plcoding.cryptotracker.crypto.presentation.coindetail.*
import com.plcoding.cryptotracker.crypto.presentation.coinlist.*
import org.koin.androidx.compose.*


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is CoinListEvent.Error -> {
                android.widget.Toast.makeText(
                    context,
                    event.error.toString(context),
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }

                            CoinListAction.OnRefresh -> {}
                        }
                    })
            }
        },

        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
        modifier = modifier
    )
}

@Composable
private fun coinUi(action: CoinListAction.OnCoinClick) = action.coinUi