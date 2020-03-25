package com.tameki.tradernet.presentation.ticker.recycler

import androidx.recyclerview.widget.DiffUtil
import com.tameki.tradernet.model.Ticker

class TickerDiffUtil(
    private val oldTickers: List<Ticker>,
    private val newTickers: List<Ticker>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTickers.size

    override fun getNewListSize(): Int = newTickers.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = try {
        oldTickers[oldItemPosition].ticker == newTickers[newItemPosition].ticker
    } catch (e: Exception) {
        false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = try {
        val oldTicker = oldTickers[oldItemPosition]
        val newTicker = newTickers[oldItemPosition]

        oldTicker.ticker == newTicker.ticker && oldTicker.lastTradePrice == newTicker.lastTradePrice &&
                oldTicker.lastTradePlatform == newTicker.lastTradePlatform &&
                oldTicker.sessionCloseChange == newTicker.sessionCloseChange &&
                oldTicker.closeChangePercent == newTicker.closeChangePercent
    } catch (e: Exception) {
        false
    }
}