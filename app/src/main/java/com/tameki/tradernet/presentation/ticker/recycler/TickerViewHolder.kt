package com.tameki.tradernet.presentation.ticker.recycler

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tameki.tradernet.R
import com.tameki.tradernet.model.ETickerPriceState
import com.tameki.tradernet.model.Ticker
import com.tameki.tradernet.utils.format
import com.tameki.tradernet.utils.ownerScope
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_ticker.*
import kotlinx.coroutines.*
import kotlin.math.absoluteValue

@SuppressLint("SetTextI18n")
class TickerViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var currentBackgroundResetJob: Job? = null

    fun onBind(ticker: Ticker) {
        ticker_icon.bind(ticker.ticker)
        ticker_code.text = ticker.ticker
        ticker_info.text = "${ticker.lastTradePlatform} | ${ticker.tickerName}"

        val sessionCloseSign = if (ticker.sessionCloseChange >= 0) "+" else "-"
        ticker_price_info.text = "${ticker.lastTradePrice.format()} ($sessionCloseSign${ticker.sessionCloseChange.absoluteValue.format()})"

        updatePriceChange(ticker)
    }

    private fun updatePriceChange(ticker: Ticker) {
        currentBackgroundResetJob?.cancel()
        currentBackgroundResetJob = null

        val isPositive = ticker.closeChangePercent >= 0
        ticker_price_change_percent.text = "${if (isPositive) "+" else "-"}${ticker.closeChangePercent.absoluteValue.format()}%"

        val color = ContextCompat.getColor(itemView.context, if (isPositive) R.color.green else R.color.red)
        ticker_price_change_percent.setTextColor(color)
        ticker_price_change_percent.setBackgroundResource(0)

        val backgroundRes = when(ticker.lastTradePriceState) {
            ETickerPriceState.DOWN -> R.drawable.bg_rounded_4dp_red
            ETickerPriceState.UP -> R.drawable.bg_rounded_4dp_green
            else -> 0
        }

        if (backgroundRes > 0) {
            ticker_price_change_percent.setBackgroundResource(backgroundRes)
            ticker_price_change_percent.setTextColor(Color.WHITE)

            currentBackgroundResetJob = itemView.ownerScope?.launch(Dispatchers.Main) {
                delay(700)
                ticker_price_change_percent?.setTextColor(color)
                ticker_price_change_percent?.setBackgroundResource(0)
            }
        }
    }
}