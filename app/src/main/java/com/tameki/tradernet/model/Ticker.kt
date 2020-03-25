package com.tameki.tradernet.model

data class Ticker(
    var ticker: String = "",
    var closeChangePercent: Double = 0.0,
    var lastTradePlatform: String = "",
    var tickerName: String = "",
    var lastTradePrice: Double = 0.0,
    var lastTradePriceState: ETickerPriceState = ETickerPriceState.NONE,
    var sessionCloseChange: Double = 0.0
)