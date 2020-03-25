package com.tameki.tradernet

import android.app.Application
import com.tameki.tradernet.data.AppConfig
import com.tameki.tradernet.data.TradernetManager
import com.tameki.tradernet.data.remote.TradernetClient

class App : Application() {

    companion object {
        lateinit var appConfig: AppConfig
        lateinit var tradernetManager: TradernetManager
    }

    override fun onCreate() {
        super.onCreate()

        appConfig = AppConfig()
        tradernetManager = TradernetManager(
            TradernetClient(appConfig)
        ).apply { init() }
    }
}