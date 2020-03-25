package com.tameki.tradernet.presentation.ticker.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tameki.tradernet.R
import com.tameki.tradernet.model.Ticker
import com.tameki.tradernet.utils.inflate

class TickerAdapter(
    private var mTickers: ArrayList<Ticker> = ArrayList()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TickerViewHolder(parent.inflate(R.layout.item_ticker))
    }

    override fun getItemCount(): Int = mTickers.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TickerViewHolder -> holder.onBind(mTickers[position])
        }
    }

    fun setTickers(tickers: List<Ticker>) {
        val diffResult = DiffUtil.calculateDiff(TickerDiffUtil(mTickers, tickers))
        mTickers.clear()
        mTickers.addAll(tickers)
        diffResult.dispatchUpdatesTo(this)
    }
}