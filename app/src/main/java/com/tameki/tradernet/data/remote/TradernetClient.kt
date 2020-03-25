package com.tameki.tradernet.data.remote

import com.tameki.tradernet.data.AppConfig
import com.tameki.tradernet.model.ETickerPriceState
import com.tameki.tradernet.model.Ticker
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class TradernetClient(
    private val appConfig: AppConfig
) {
    private val mSocket: Socket = IO.socket(appConfig.wsPath)

    private var mListener: TickersUpdateListener? = null
    private var mCachedTickers = HashMap<String, Ticker>()

    private fun onTickersUpdate(tickersJson: JSONArray) {
        val changedTickers = ArrayList<Ticker>()

        for (i in 0 until tickersJson.length()) {
            val it = tickersJson[i] as JSONObject

            val tickerCode = it["c"].toString()
            val cachedTicker = mCachedTickers[tickerCode] ?: Ticker()
            cachedTicker.ticker = tickerCode

            if (it.has("ltr")) {
                cachedTicker.lastTradePlatform = it["ltr"].toString() // Биржа последней сделки
            }
            if (it.has("name")) {
                cachedTicker.tickerName = it["name"].toString() // Название бумаги
            }
            if (it.has("chg")) {
                cachedTicker.sessionCloseChange = it["chg"].toString().toDouble() // Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
            }
            if (it.has("pcp")) {
                cachedTicker.closeChangePercent = it["pcp"].toString().toDouble() // Изменение в процентах относительно цены закрытия предыдущей торговой сессии
            }
            if (it.has("ltp")) {
                val newPrice = it["ltp"].toString().toDouble()

                if (cachedTicker.lastTradePrice != 0.0) {
                    when {
                        newPrice > cachedTicker.lastTradePrice -> cachedTicker.lastTradePriceState = ETickerPriceState.UP
                        newPrice < cachedTicker.lastTradePrice -> cachedTicker.lastTradePriceState = ETickerPriceState.DOWN
                        else -> cachedTicker.lastTradePriceState = ETickerPriceState.NONE
                    }
                }

                cachedTicker.lastTradePrice = newPrice // Цена последней сделки
            }

            mCachedTickers[tickerCode] = cachedTicker
            changedTickers.add(cachedTicker)
        }

        mListener?.onUpdate(changedTickers)
    }

    fun connect(listener: TickersUpdateListener) {
        mListener = listener

        mSocket.on(Socket.EVENT_CONNECT) {
            if (mSocket.connected()) {
                val tickersToWatchJSON = JSONArray()
                appConfig.tickers.forEach { tickersToWatchJSON.put(it) }
                mSocket.emit(SUBSCRIBE_TO_TICKETS_CHANGE_EVENT, tickersToWatchJSON)
            }
        }.on(TICKETS_UPDATE_EVENT) { tickersData ->
            (tickersData is Array<*>).let {
                val data = (tickersData.first() as JSONObject)[TICKETS_UPDATE_EVENT] as JSONArray
                onTickersUpdate(data)
            }
        }

        mSocket.connect()
    }

    fun disconnect() {
        mSocket.close()
    }

    interface TickersUpdateListener {
        fun onUpdate(tickers: Collection<Ticker>)

        fun onError(e : Exception)
    }

    companion object {
        private const val SUBSCRIBE_TO_TICKETS_CHANGE_EVENT = "sup_updateSecurities2"
        private const val TICKETS_UPDATE_EVENT = "q"
    }
}