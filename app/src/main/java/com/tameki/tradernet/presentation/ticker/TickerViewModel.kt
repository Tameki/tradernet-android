package com.tameki.tradernet.presentation.ticker

import androidx.lifecycle.ViewModel
import com.tameki.tradernet.App

class TickerViewModel : ViewModel() {
    val tickers = App.tradernetManager.getTickers()
}
