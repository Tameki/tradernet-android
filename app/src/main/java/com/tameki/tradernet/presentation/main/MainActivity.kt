package com.tameki.tradernet.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tameki.tradernet.presentation.ticker.TickerFragment

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		supportFragmentManager.beginTransaction()
			.add(android.R.id.content, TickerFragment.newInstance())
			.commit()
	}
}
