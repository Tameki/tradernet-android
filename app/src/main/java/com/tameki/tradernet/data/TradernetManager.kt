package com.tameki.tradernet.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tameki.tradernet.data.remote.TradernetClient
import com.tameki.tradernet.model.Ticker
import java.lang.Exception

class TradernetManager(
    private val apiClient: TradernetClient
) : TradernetClient.TickersUpdateListener {
    private val cachedTickers = HashMap<String, Ticker>()
    private val tickersLiveData = MutableLiveData<List<Ticker>>()

    fun init() {
        apiClient.connect(this)
    }

    fun getTickers(): LiveData<List<Ticker>> {
        return tickersLiveData
    }

    override fun onUpdate(tickers: Collection<Ticker>) {
        tickers.forEach {
            cachedTickers[it.ticker] = it.copy()
        }
        tickersLiveData.postValue(cachedTickers.values.toList())
    }

    override fun onError(e: Exception) {

    }

    fun dispose() {
        apiClient.disconnect()
    }
}