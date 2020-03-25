package com.tameki.tradernet.presentation.widgets

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition
import com.tameki.tradernet.utils.GlideBitmapTarget
import java.util.*

class TickerIcon : AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun bind(tickerCode: String) {
        val tickerIconUrl = "https://tradernet.ru/logos/get-logo-by-ticker?ticker=" +
                tickerCode.toLowerCase(Locale.US)

        Glide.with(this)
            .asBitmap()
            .load(tickerIconUrl)
            .into(object : GlideBitmapTarget() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if (resource.byteCount > 4) {
                        this@TickerIcon.visibility = View.VISIBLE
                        this@TickerIcon.setImageBitmap(resource)
                    } else {
                        this@TickerIcon.visibility = View.GONE
                    }
                }
            })
    }
}