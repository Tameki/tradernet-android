package com.tameki.tradernet.presentation.ticker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tameki.tradernet.R
import com.tameki.tradernet.presentation.ticker.recycler.TickerAdapter
import com.tameki.tradernet.utils.visible
import kotlinx.android.synthetic.main.fragment_ticker.*

class TickerFragment : Fragment() {

    private lateinit var mViewModel: TickerViewModel
    lateinit var mAdapter: TickerAdapter

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = TickerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ticker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(TickerViewModel::class.java)

        mViewModel.tickers.observe(viewLifecycleOwner, Observer {
            mAdapter.setTickers(it)

            val isLoaded = it.size > 5
            ticker_shimmer.visible = !isLoaded
            ticker_recycler.visible = isLoaded
        })
    }

    override fun onResume() {
        super.onResume()
        ticker_shimmer?.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        ticker_shimmer?.stopShimmer()
    }

    //endregion

    private fun initView() {
        ticker_recycler.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false)

        ticker_recycler.adapter = mAdapter
        (ticker_recycler.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.bg_divider)!!)
        ticker_recycler?.addItemDecoration(itemDecorator)
    }

    companion object {
        fun newInstance() = TickerFragment()
    }

}
